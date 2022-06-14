"use strict";

const _source = {};
const _target = {};

function handleFieldClick(e) {
    e.preventDefault();
    const $field = getClickedField(e);

    if ($field) {
        if (isPlayerPawnInField($field)) {
            handleClickOnPawnInField($field);
        } else {
            handleClickOnField($field);
        }
    }
}

function getClickedField(e) {
    if (e.target.parentElement.classList.contains('field')) {
        return e.target.parentElement;
    } else if (e.target.classList.contains('field')) {
        return e.target;
    }
    return null;
}

function isPlayerPawnInField($field) {
    return $field.hasAttribute('data-rank');
}

function handleClickOnPawnInField($field) {
    if (_game.state === _STATE_LOBBY) {
        pickupPieceFromField($field);
    } else if (_game.state === _STATE_PLAYING) {
        setSourceField($field);
        highlightField($field);
    }
}

function setSourceField($field) {
    _source.row = +$field.dataset.row;
    _source.col = +$field.dataset.col;
}

function pickupPieceFromField($field) {
    setMessage(`Click on the board to place your ${$field.dataset.rank}`);
    markSelectedPiece($field.dataset.rank);
    removePawnFromField($field);
}

function handleClickOnField($field) {
    if (_game.state === _STATE_LOBBY) {
        dropPieceToField(getSelectedPiece(), $field);
    } else if (_game.state === _STATE_PLAYING) {
        setTargetField($field);
        movePawnToField($field);
    }
}

function setTargetField($field) {
    _target.row = +$field.dataset.row;
    _target.col = +$field.dataset.col;
}

function getSelectedPiece() {
    return document.querySelector('.selected');
}

function getMovingPawnRank() {
    return document.querySelector('.moving').dataset.rank;
}

function insertBoardFieldsHtml() {
    for (let row = 0; row < config.defaultBoardSize.rows; row++) {
        for (let col = 0; col < config.defaultBoardSize.cols; col++) {
            document.querySelector('#board')
                .insertAdjacentElement('beforeend', generateFieldHtml(row, col));
        }
    }
}

function generateFieldHtml(row, col) {
    const $field = document.createElement('li');

    $field.className = 'field';
    $field.dataset.row = row;
    $field.dataset.col = col;
    setBlockedField($field, row < 6);

    return $field;
}

function setBlockedField($field, condition) {
    if (condition) {
        $field.classList.add("blocked");
    }
}

function unsetBlockedField($field) {
    if ($field.classList.contains("blocked") && _game.state !== _STATE_LOBBY) {
        $field.classList.remove("blocked");
    }
}

function setMessage(msg, priority = 0) {
    if (priority > _game.lastMessagePriority) {
        _game.lastMessagePriority = priority;
        document.querySelector('#messages').innerHTML = msg;
    }

    if (_game.lastMessagePriority > 0) {
        _game.lastMessagePriority--;
    } else {
        document.querySelector('#messages').innerHTML = msg;
    }
}

function getBoard() {
    const board = createTenByTenArray();

    document.querySelectorAll('.field').forEach($field => {
        if ($field.dataset.rank) {
            board[$field.dataset.row][$field.dataset.col] = { rank: $field.dataset.rank };
        } else {
            board[$field.dataset.row][$field.dataset.col] = null;
        }
    });

    return board;
}

function getBoardPreparedForAPI() {
    const storedBoard = getBoard();
    const apiBoard = createTenByTenArray();

    for (let row = 0; row < config.defaultBoardSize.rows; row++) {
        for (let col = 0; col < config.defaultBoardSize.cols; col++) {
            apiBoard[row][col] = storedBoard[row][col] ? storedBoard[row][col]['rank'] : null;
        }
    }

    return apiBoard;
}

function createTenByTenArray() {
    return Array(10).fill(null).map(()=>Array(10).fill(null));
}

function clearBoard() {
    document.querySelector("#board").innerHTML = '';
    insertBoardFieldsHtml();
}

function refreshBoardWith(pawns) {
    document.querySelectorAll('.field').forEach($field => {
        if (pawnsAreDifferent(pawns[$field.dataset.row][$field.dataset.col], getPawnFromField($field))) {
            loadPawnInField(pawns[$field.dataset.row][$field.dataset.col], $field);
        }
        unsetBlockedField($field);
    });
}

function pawnsAreDifferent(newPawn, oldPawn) {
    return JSON.stringify(newPawn) !== JSON.stringify(oldPawn);
}

function loadPawnInField(pawn, $field) {
    removePawnFromField($field);

    if (isPlayerPawn(pawn)) {
        insertPawnHtml($field, pawn.rank, _game.playerSkin);
    } else if (isEnemyPawn(pawn)) {
        insertImageHtml($field, `assets/media/${_game.enemySkin}-background.png`);
    }
}

function getPawnFromField($field) {
    if ($field.dataset.rank) {
        return { 'player': _game.player, 'rank': $field.dataset.rank };
    } else if (!!$field.querySelector('img')){
        return { 'player': getEnemyPlayer() };
    } else {
        return null;
    }
}

function getEnemyPlayer() {
    return _game.player === _PLAYER_RED ? _PLAYER_BLUE : _PLAYER_RED;
}

function isPlayerPawn(pawn) {
    return pawn && pawn.rank;
}

function isEnemyPawn(pawn) {
    return pawn && pawn.player !== _game.player;
}

function removePawnFromField($field) {
    if ($field.dataset.rank) {
        decrementUsedPieces($field.dataset.rank, _LIST_OF_PLAYER_PIECES);
        $field.removeAttribute('data-rank');
    } else if ($field.dataset.enemy) {
        decrementUsedPieces($field.dataset.enemy, _LIST_OF_ENEMY_PIECES);
        $field.removeAttribute('data-enemy');
    }
    $field.innerHTML = '';
    unmarkHighlighted();
}

function validateAllPiecesAreDeployed() {
    document.querySelectorAll('#your-team .piece').forEach($piece => {
        const count = $piece.querySelector('.count').innerHTML;
        const used = $piece.querySelector('.used').innerHTML;
        if (used < count) {
            throw new Error("Deploy all pieces to continue");
        }
    });
}
