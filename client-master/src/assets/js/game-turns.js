"use strict";

const _PLAYER_BLUE = "blue";
const _PLAYER_RED = "red";

function startPlayerTurn() {
    if (_game.player === _PLAYER_BLUE) {
        setGameStateTo(_STATE_WAITING);
        fetchWaitForYourTurn();
    } else {
        finishWaitingForTurn();
    }
}

function fetchWaitForYourTurn() {
    if (_game.state !== _STATE_LOBBY) {
        fetchMoves(_game.gameId).then((moves) => {
            if (_game.player === getLastMove(moves).player) {
                continueWaitingForTurn();
            } else {
                finishWaitingForTurn(moves);
            }
        });
    }
}

function continueWaitingForTurn() {
    _game.waitingSeconds++;
    setMessage(`Waiting for enemy player to move (${_game.waitingSeconds}s)`);
    setTimeout(fetchWaitForYourTurn, _TIME_BETWEEN_API_CALLS_IN_MS); // prevent DoS
}

function finishWaitingForTurn(moves) {
    _game.waitingSeconds = 0;
    setMessage("It is your turn");

    if (_game.state === _STATE_WAITING) {
        setGameStateTo(_STATE_PLAYING);
        fetchPawnsAndRefreshBoard(moves);
    }
}

function fetchPawnsAndRefreshBoard(moves) {
    fetchPawnPositions(_game.gameId).then(pawns => {
        refreshBoardWith(pawns);
        handleLastAttackOutcome(moves);
    });
}
