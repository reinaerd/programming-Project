"use strict";

const _START_ROW_OF_NORMAL_TERRITORY = 4;

function movePawnToField($field) {
    try {
        validateMoveToField($field);
        moveOrInfiltrateToField($field);
    } catch (e) {
        setMessage(e);
    }
}

function moveOrInfiltrateToField($field) {
    if (isInfiltratorMove() && isPlaying() && getPawnFromField($field)) {
        generateInfiltrators();
        show("infiltrate");
    } else {
        fetchMakeMoveAndWaitTurn();
    }
}

function isInfiltratorMove() {
    return getMovingPawnRank() === _RANK_INFILTRATOR && isInEnemyTerritory() && _game.infiltrate === null;
}

function isPlaying() {
    return _game.state === _STATE_PLAYING;
}

function generateInfiltrators() {
    const $infiltrators = document.querySelector("#infiltrate ul");
    $infiltrators.innerHTML = '';

    for (const piece of getPieces()) {
        const $piece = document.createElement("li");
        insertInfiltrateHtml($piece, piece);
        $infiltrators.insertAdjacentElement("beforeend", $piece);
    }
}

function insertInfiltrateHtml($parent, rank) {
    $parent.classList.add('infiltrator');
    $parent.dataset.rank = rank;
    insertImageHtml($parent, getImgSrc(rank, _game.playerSkin));
    insertNameHtml($parent, getName(rank));
}

function highlightField($field) {
    unmarkHighlighted();
    $field.classList.add('moving');
}

function unmarkHighlighted() {
    if (document.querySelector('.moving')) {
        document.querySelector('.moving').classList.toggle('moving');
    }
}

function validateMoveToField($field) {
    if (config.clientValidation) {
        validatePawnIsAllowToMove(getMovingPawnRank());
        validateMoveDistanceToField(getMovingPawnRank());
        validateMoveIsNotDiagonal();
        validateTargetFieldIsNotWater($field);
        validateFieldIsNotOccupied($field);
    }
}

function validateFieldIsNotOccupied($field) {
    if ($field.dataset.rank) {
        throw new Error('Field occupied by one of your pieces');
    }
}

function validateTargetFieldIsNotWater($field) {
    if (isWater(+$field.dataset.row, +$field.dataset.col)) {
        throw new Error('You can not move to the water');
    }
}

function isWater(row, col) {
    if (row === 4 || row === 5) {
        if (col === 2 || col === 3 || col === 6 || col === 7) {
            return true;
        }
    }
    return false;
}

function validateMoveDistanceToField(rank) {
    if (isMoreThanTilesDistance(1)) {
        if (rank === _RANK_SCOUT) {
            validateMoveScout();
        } else if (isInfiltratorMove() && isPawnInField(getField(_target.row, _target.col))) {
            validateMoveInfiltrator();
        } else {
            throw new Error ('You can only move to an adjacent field');
        }
    }
}

function validateMoveInfiltrator() {
    if (isMoreThanTilesDistance(2)) {
        throw new Error ('You can only infiltrator 2 tiles away');
    }
}

function isInEnemyTerritory() {
    return _source.row < _START_ROW_OF_NORMAL_TERRITORY && _target.row < _START_ROW_OF_NORMAL_TERRITORY;
}

function validateMoveScout() {
    validateMoveOverOtherPiece();
    validateMoveOverWater();
}

function validateMoveOverOtherPiece() {
    if (isMoveOverOtherPiece()) {
        throw new Error ('You can not move over other piece');
    }
}

function isMoveOverOtherPiece() {
    if (isVerticalMove()) {
        if (isFromTopToBottom()) {
            return isAnyPawnInTrajectory(_source.row, _target.row, true);
        } else {
            return isAnyPawnInTrajectory(_target.row, _source.row, true);
        }
    } else {
        if (isFromLeftToRight()) {
            return isAnyPawnInTrajectory(_source.col, _target.col, false);
        } else {
            return isAnyPawnInTrajectory(_target.col, _source.col, false);
        }
    }
}

function isAnyPawnInTrajectory(start, end, isVertical) {
    for (let i = start + 1; i <= end - 1; i++) {
        if (isPawnInField(getField(isVertical ? i : _source.row, isVertical ? _source.col : i))) {
            return true;
        }
    }
    return false;
}

function isFromTopToBottom() {
    return _source.row < _target.row;
}

function isFromLeftToRight() {
    return _source.col < _target.col;
}

function isPawnInField($field) {
    return !!$field.dataset.rank || !!$field.querySelector('img');
}

function validateMoveOverWater() {
    if (isMoveOverWater()) {
        throw new Error ('You can not move over water');
    }
}

function isMoveOverWater() {
    if (isSourceOnLakeHorizontal()) {
        return isMoveFromLeftOverLeftLake() || isMoveFromRightOverRightLake() || isMoveFromMiddleOverAnyLake();
    } else if (isSourceOnLakeVertical()) {
        return isMoveFromTopOverLake() || isMoveFromBottomOverLake();
    }
    return false;
}

function isSourceOnLakeVertical() {
    return (_source.col === 2 || _source.col === 3 || _source.col === 6 || _source.col === 7);
}

function isMoveFromTopOverLake() {
    return (_source.row < 4 && _target.row > 5);
}

function isMoveFromBottomOverLake() {
    return (_source.row > 5 && _target.row < 4);
}

function isSourceOnLakeHorizontal() {
    return (_source.row === 4 || _source.row === 5);
}

function isMoveFromLeftOverLeftLake() {
    return (_source.col < 2 && _target.col > 2);
}

function isMoveFromRightOverRightLake() {
    return (_source.col > 7 && _target.col < 7);
}

function isMoveFromMiddleOverAnyLake() {
    return (_source.col > 3 && _source.col < 6) && (_target.col < 3 || _target.col > 6);
}

function isMoreThanTilesDistance(distance) {
    return isFurtherThan(_source.row, _target.row, distance) || isFurtherThan(_source.col, _target.col, distance);
}

function isFurtherThan(source, target, tiles) {
    return (source - target) > tiles || (target - source) > tiles;
}

function validateMoveIsNotDiagonal() {
    if (isDiagonalMove()) {
        throw new Error('You can not move diagonally');
    }
}

function isDiagonalMove() {
    if (!_source.row && !_target.row) {
        return false;
    }

    return !isHorizontalMove() && !isVerticalMove();
}

function isHorizontalMove() {
    return _source.row === _target.row && _target.col !== _source.col;
}

function isVerticalMove() {
    return _source.row !== _target.row && _target.col === _source.col;
}

function validatePawnIsAllowToMove(rank) {
    if (rank === _RANK_FLAG || rank === _RANK_BOMB) {
        throw new Error(`The ${rank} cannot move`);
    }
}

function fetchMakeMoveAndWaitTurn() {
    fetchMakeMove(_game.gameId, _game.playerToken, _source, _target)
        .then(move => {
            processMoveResponse(move);
        });
}

function fetchMakeMoveInfiltrateAndWaitTurn() {
    fetchMakeMoveInfiltrate(_game.gameId, _game.playerToken, _source, _target, _game.infiltrate)
        .then(move => {
            if (typeof move != "undefined") {
                processMoveResponse(move, true);
                _game.infiltrate = null;
            } else {
                throw new Error("server returned undefined movement");

            }
        });
}

function processMoveResponse(move, isInfiltrate) {
    makeMoveOrAttackFromTo(move, getSourceField(), getTargetField(), isInfiltrate);
    if (_game.won === null) {
        setGameStateTo(_STATE_WAITING);
        fetchWaitForYourTurn();
    }
}

function makeMoveOrAttackFromTo(move, $fromField, $toField, isInfiltrate) {
    if (move.attack) {
        makeAttackFromTo(move.attack, $fromField, $toField, isInfiltrate);
    } else {
        makeMoveFromTo($fromField, $toField);
    }
}

function getSourceField() {
    return getField(_source.row, _source.col);
}

function getTargetField() {
    return getField(_target.row, _target.col);
}

function getField(row, col) {
    return document.querySelector(`[data-row="${row}"][data-col="${col}"]`);
}

function makeMoveFromTo($fromField, $toField) {
    insertPawnHtml($toField, $fromField.dataset.rank, _game.playerSkin);
    highlightField($toField);
    setSourceField($toField);
    removePawnFromField($fromField);
}

function getLastMove(moves) {
    if (typeof moves !== "undefined") {
        return moves[moves.length - 1];
    }
    return null;
}
