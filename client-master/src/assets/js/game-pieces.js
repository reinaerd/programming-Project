"use strict";

const _LIST_OF_PLAYER_PIECES = 'your-team';
const _LIST_OF_ENEMY_PIECES = 'enemy-team';
const _PLAYER_PIECES_USED_AT_START = 0;
const _RANK_FLAG = "flag";
const _RANK_BOMB = "bomb";
const _RANK_SCOUT = "scout";
const _RANK_INFILTRATOR = "infiltrator";

const _gamePieces = {
    'count': null,
    'power': {
        'marshal': 10,
        'general': 9,
        'colonel': 8,
        'major': 7,
        'captain': 6,
        'lieutenant': 5,
        'sergeant': 4,
        'miner': 3,
        'scout': 2,
        'spy': 1,
        'infiltrator': 1,
        'bomb': 0,
        'flag': 0
    }
};

function handlePieceClick(e) {
    e.preventDefault();

    const $piece = getClickedPiece(e);

    if (_game.state === _STATE_LOBBY && $piece) {
        markSelectedPiece($piece.dataset.rank);
    }
}

function fetchAndInsertPieces(listOfPieces) {
    fetchPieces(_game.version).then(response => {
        _gamePieces['count'] = response;
        insertPieces(listOfPieces);
    });
}

function insertPieces(listOfPieces) {
    if (listOfPieces === _LIST_OF_PLAYER_PIECES) {
        loadGamePieces(listOfPieces, _game.playerSkin);
    } else {
        loadGamePieces(listOfPieces, _game.enemySkin);
    }
}

function getPieces() {
    return getSortedPieces(Object.keys(_gamePieces['count']));
}

function loadGamePieces(listOfPieces, skin) {
    const $list = document.querySelector(`#${listOfPieces}`).querySelector('ul');
    $list.innerHTML = '';

    getSortedPieces(Object.keys(_gamePieces['count'])).forEach(rank => {
        const $li = document.createElement('li');
        insertPiece($li, rank, skin);
        $list.insertAdjacentElement('beforeend', $li);
    });
}

function getSortedPieces(array) {
    return array.sort((a, b) => _gamePieces['power'][b] - _gamePieces['power'][a]);
}

function insertPiece($parent, rank, skin) {
    if ($parent && rank) {
        $parent.classList.add('piece');
        $parent.dataset.rank = rank;

        insertImageHtml($parent, getImgSrc(rank, skin));
        insertNameHtml($parent, getName(rank));
        insertUsedHtml($parent, getUsedPieces(rank, skin));
        $parent.insertAdjacentHTML('beforeend', '<p class="used-count">/</p>');
        insertCountHtml($parent, getCount(rank));
        insertPowerHtml($parent, getPower(rank));
    }
}

function getUsedPieces(rank, skin) {
    if (skin === _game.playerSkin) {
        return _PLAYER_PIECES_USED_AT_START;
    }

    return getCount(rank);
}

function insertPawnHtml($parent, rank, skin) {
    $parent.classList.add('piece');
    $parent.dataset.rank = rank;
    insertImageHtml($parent, getImgSrc(rank, skin));
    insertPowerHtml($parent, getPower(rank));
    incrementUsedPieces(rank);
}

function insertImageHtml($parent, src) {
    const $image = document.createElement('img');
    $image.src = src;
    $parent.insertAdjacentElement('beforeend', $image);
}

function insertNameHtml($parent, name) {
    const $name = document.createElement('p');
    $name.innerHTML = name;
    $name.className = 'name';
    $parent.insertAdjacentElement('beforeend', $name);
}

function insertUsedHtml($parent, used) {
    const $quantity = document.createElement('p');
    $quantity.innerHTML = used;
    $quantity.className = 'used';
    $parent.insertAdjacentElement('beforeend', $quantity);
}

function insertCountHtml($parent, count) {
    const $quantity = document.createElement('p');
    $quantity.innerHTML = count;
    $quantity.className = 'count';
    $parent.insertAdjacentElement('beforeend', $quantity);
}

function insertPowerHtml($parent, power) {
    if (power) {
        const $power = document.createElement('p');
        $power.innerHTML = power;
        $power.className = 'power';
        $parent.insertAdjacentElement('beforeend', $power);
    }
}

function unmarkSelectedPieces() {
    document.querySelectorAll('.selected').forEach( $piece => {
        $piece.classList.remove('selected');
    });
}

function markSelectedPiece(rank) {
    unmarkSelectedPieces();
    document.querySelector(`#your-team [data-rank=${rank}]`).classList.toggle('selected');
    setMessage(`Click on the board to place your ${rank}`);
}

function getClickedPiece(e) {
    if (e.target.parentElement.classList.contains('piece')) {
        return e.target.parentElement;
    } else if (e.target.classList.contains('piece')) {
        return e.target;
    }
    return null;
}

function dropPieceToField($piece, $field) {
    try {
        validatePieceSelection($piece);
        validatePieceCount($piece);
        validatePresetField($field);
        setMessage(`Click on the board to place your ${$piece.dataset.rank}`);
        insertPawnHtml($field, $piece.dataset.rank, _game.playerSkin);
    } catch (error) {
        setMessage(error);
    }
}

function validatePresetField($field) {
    if (_game.state === _STATE_LOBBY && $field.dataset.row < 6) {
        throw new Error('You can only place your pieces on your side (bottom)');
    }
}

function validatePieceSelection($piece) {
    if (!$piece) {
        throw new Error('Select a piece first');
    }
    return true;
}

function validatePieceCount($piece) {
    const used = $piece.querySelector('.used').innerHTML;
    const count = $piece.querySelector('.count').innerHTML;

    if (used >= count) {
        throw new Error(`Can not place ${$piece.dataset.rank} because there are none left`);
    }
    return true;
}

function incrementUsedPieces(rank) {
    const $usedScore = document.querySelector(`#${_LIST_OF_PLAYER_PIECES} [data-rank=${rank}] p.used`);
    if ($usedScore) {
        document.querySelector(`#${_LIST_OF_PLAYER_PIECES} [data-rank=${rank}] p.used`).innerHTML++;
    }
}

function decrementUsedPieces(rank, team) {
    const $usedScore = document.querySelector(`#${team} [data-rank=${rank}] p.used`);
    if ($usedScore) {
        document.querySelector(`#${team} [data-rank=${rank}] p.used`).innerHTML--;
    }
}

function getPower(rank) {
    return _gamePieces['power'][rank];
}

function getCount(rank) {
    return _gamePieces['count'][rank];
}

function getName(rank) {
    return rank.charAt(0).toUpperCase() + rank.slice(1);
}

function getImgSrc(rank, skin) {
    return `assets/media/ranks/${skin}/${rank}.png`;
}

