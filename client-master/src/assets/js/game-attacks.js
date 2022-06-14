"use strict";

const _ATTACK_MESSAGE_PRIORITY = 6;
const _ATTACKER = "attacker";
const _DEFENDER = "defender";
const _WINNER = "winner";
const _DRAW = "draw";

function makeAttackFromTo(attack, $fromField, $toField, isInfiltrate) {
    if (attack[_WINNER] === _DRAW) {
        attackDrawn(attack, $toField, $fromField);
    } else if (attack[_WINNER] === _ATTACKER) {
        attackWon(attack, $toField, $fromField, isInfiltrate);
    } else if (attack[_WINNER] === _DEFENDER) {
        attackLost(attack, $fromField, isInfiltrate);
    } else {
        throw new Error("Unknown attack outcome:" + attack[_WINNER]);
    }
}

function handleLastAttackOutcome(moves) {
    const lastMove = getLastMove(moves);
    if (lastMove && lastMove.attack) {
        handleAttackOutcome(lastMove.attack);
    }
}

function handleAttackOutcome(attack) {
    if (attack[_WINNER] === _DRAW) {
        decrementUsedPieces(attack[_ATTACKER], _LIST_OF_ENEMY_PIECES);
        notifyBothPawnsKilled(attack[_DEFENDER], attack[_ATTACKER]);
    } else if (attack[_WINNER] === _DEFENDER) {
        processWinnerOutcome(attack);
    } else if (attack[_WINNER] === _ATTACKER) {
        notifyEnemyPawnKilledPlayerPawn(attack[_ATTACKER], attack[_DEFENDER]);
    }
}

function processWinnerOutcome(attack) {
    if (attack[_ATTACKER] !== _RANK_INFILTRATOR) {
        decrementUsedPieces(attack[_ATTACKER], _LIST_OF_ENEMY_PIECES);
        notifyPlayerPawnKilledEnemyPawn(attack[_DEFENDER], attack[_ATTACKER]);
    } else {
        notifyEnemyInfiltrationFailed(attack[_ATTACKER], attack[_DEFENDER]);
    }
}

function attackDrawn(attack, $defenderField, $attackerField) {
    notifyBothPawnsKilled(attack[_ATTACKER], attack[_DEFENDER]);
    removeEnemyPieceFromField(attack[_DEFENDER], $defenderField);
    removePlayerPieceFromField(attack[_ATTACKER], $attackerField);
}

function attackWon(attack, $defenderField, $attackerField, isInfiltrate) {
    notifyPlayerPawnKilledEnemyPawn(attack[_ATTACKER], attack[_DEFENDER]);
    removeEnemyPieceFromField(attack[_DEFENDER], $defenderField);

    if (!isInfiltrate) {
        makeMoveFromTo($attackerField, $defenderField);
    }
}

function attackLost(attack, $attackerField, isInfiltrate) {
    notifyPlayerInfiltrationFailed(attack[_ATTACKER], attack[_DEFENDER]);

    if (!isInfiltrate) {
        notifyEnemyPawnKilledPlayerPawn(attack[_DEFENDER], attack[_ATTACKER]);
        removePlayerPieceFromField(attack[_ATTACKER], $attackerField);
    }
}

function notifyBothPawnsKilled(playerPawn, enemyPawn) {
    setMessage(`Your ${playerPawn} attacked a ${enemyPawn}, both died`, _ATTACK_MESSAGE_PRIORITY);
}

function notifyPlayerPawnKilledEnemyPawn(playerPawn, enemyPawn) {
    setMessage(`Your ${playerPawn} killed a ${enemyPawn}`, _ATTACK_MESSAGE_PRIORITY);
}

function notifyEnemyPawnKilledPlayerPawn(enemyPawn, playerPawn) {
    setMessage(`A ${enemyPawn} killed your ${playerPawn}. It is your turn.`, _ATTACK_MESSAGE_PRIORITY);
}

function notifyPlayerInfiltrationFailed(playerPawn, enemyPawn) {
    setMessage(`Your ${playerPawn} failed infiltrating ${enemyPawn}`, _ATTACK_MESSAGE_PRIORITY);
}

function notifyEnemyInfiltrationFailed(playerPawn, enemyPawn) {
    setMessage(`Enemy ${playerPawn} guessed your ${enemyPawn} incorrectly`, _ATTACK_MESSAGE_PRIORITY);
}

function removeEnemyPieceFromField(pawn, $field) {
    addEnemyRankToField(pawn, $field);
    removePawnFromField($field);
}

function removePlayerPieceFromField(pawn, $field) {
    removePawnFromField($field);
}

function addEnemyRankToField(rank, $field) {
    $field.dataset.enemy = rank;
}
