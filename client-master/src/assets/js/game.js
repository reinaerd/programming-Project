"use strict";

const _STATE_LOBBY = 'lobby';
const _STATE_WAITING = 'waiting';
const _STATE_PLAYING = 'playing';
const _SKIN_SONIC = 'sonic';
const _SKIN_MARIO = 'mario';
const _MINIMUM_MOVES_TO_START_GAME = 2;

const _game = {
    'roomId': null,
    'gameId': null,
    'player': null,
    'playerToken': null,
    'version': null,
    'playerSkin': null,
    'enemySkin': null,
    'state': null,
    'waitingSeconds': 0,
    'lastMessagePriority': 0,
    'won': null,
    'infiltrate': null
};

function fetchRoomAndWaitForSecondPlayer(roomId) {
    fetchRoom(roomId)
        .then(response => {
            getGameInfoFromResponse(response);
            fetchWaitForSecondPlayer();
        });
}

function getGameInfoFromResponse(response) {
    _game.gameId = response.gameId;
    _game.player = response.player;
    _game.playerToken = response.playerToken;
}

function fetchWaitForSecondPlayer() {
    fetchMoves(_game.gameId)
        .then(moves => {
            if (moves.length >= _MINIMUM_MOVES_TO_START_GAME) {
                startGame();
                startTimer();
            } else {
                setTimeout(fetchWaitForSecondPlayer, _TIME_BETWEEN_API_CALLS_IN_MS); // prevent DoS
            }
        });
}

function startGame() {
    setGameStateTo(_STATE_PLAYING);
    loadPlayingInterface();

    fetchPawnPositions(_game.gameId)
        .then(pawns => {
            refreshBoardWith(pawns);
            getLoserPolling();
            startPlayerTurn();
        });
}

function loadPlayingInterface() {
    showPlayingScreen();
    fetchAndInsertPieces(_LIST_OF_ENEMY_PIECES);
    hide("start");
    show("game-board");
}

function setGameStateTo(state) {
    _game.state = state;
}

function getLoserPolling() {
    fetchGetLosers(_game.gameId)
        .then(losers => {
            handleLosers(losers);
        });
}

function handleLosers(losers) {
    if (isInLosers(_game.player, losers) && isInLosers(getEnemyPlayer(), losers)) {
        showEndScreen(_OUTCOME_DRAW);
    } else if (isInLosers(getEnemyPlayer(), losers)) {
        showEndScreen(_OUTCOME_WINNER);
    } else if (isInLosers(_game.player, losers)) {
        showEndScreen(_OUTCOME_LOSER);
    } else {
        setTimeout(getLoserPolling, _TIME_BETWEEN_API_CALLS_IN_MS);
    }
}

function isInLosers(player, losers) {
    return losers[0] === player || losers[1] === player;
}

function handleInfiltratorActions(e) {
    const $infiltrator = getClickedInfiltrator(e);

    if ($infiltrator) {
        unmarkAllInfiltrators();
        $infiltrator.classList.toggle('infiltrate');
        _game.infiltrate = $infiltrator.dataset.rank;
    }
}

function getClickedInfiltrator(e) {
    if (e.target.parentElement.classList.contains('infiltrator')) {
        return e.target.parentElement;
    } else if (e.target.classList.contains('infiltrator')) {
        return e.target;
    }
    return null;
}

function unmarkAllInfiltrators() {
    document.querySelectorAll('.infiltrate').forEach($infiltrator => {
        $infiltrator.classList.remove('infiltrate');
    });
}

function resetGameConfig() {
    _game.infiltrate = null;
    _game.waitingSeconds = null;
    _game.won = null;
    _game.lastMessagePriority = null;
}

