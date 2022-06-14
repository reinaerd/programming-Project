"use strict";

const _OUTCOME_WINNER = "winner";
const _OUTCOME_LOSER = "loser";
const _OUTCOME_DRAW = "draw";

function handleButtonNavigation(e) {
    if (e.target.hasAttribute('data-target')) {
        handleNavigation(e);
    }
}

function handleNavigation(e) {
    hide(e.target.dataset.current);

    if (!(e.target.dataset.current === "waitingSeconds-for-player" && e.target.classList.contains("non-clickable-button"))){
        show(e.target.dataset.target);
    }
}

function show(id) {
    if (document.querySelector(`#${id}`).classList.contains('hidden')) {
        document.querySelector(`#${id}`).classList.remove('hidden');
    }
}

function hide(id) {
    document.querySelector(`#${id}`).classList.add('hidden');
}

function hideAll() {
    document.querySelectorAll('section').forEach(s => s.classList.add('hidden'));
    document.querySelector('#enemy-team').classList.add('hidden');
}

function showVersionScreen() {
    setMessage("Select version");
    clearBoard();
    document.querySelector('#popup').classList.add('hidden');
}

function showTeamScreen() {
    setMessage("Select team");
}

function showPresetScreen() {
    show('options');
    hide('enemy-team');
    setMessage('Place your pieces on the board');
    fetchAndInsertPieces(_LIST_OF_PLAYER_PIECES);
    updateFooterButton( 'Continue', 'setPreset', 'play');
}

function showLobbyScreen() {
    unmarkSelectedPieces();
}

function showPlayingScreen() {
    hide('options');
    show('enemy-team');
    updateFooterButton('Forfeit game','forfeitGame');
}

function showEndScreen(outcome) {
    setGameStateTo(_STATE_LOBBY);
    _game.won = outcome;

    if (outcome === _OUTCOME_WINNER) {
        showWinnerScreen();
    } else if (outcome === _OUTCOME_LOSER) {
        showLoserScreen();
    } else if (outcome === _OUTCOME_DRAW) {
        showDrawScreen();
    }

    stopTimer();
    updateStatistics();
    updateFooterButton('Return to menu', 'backToMenu', 'versions', 'on-top');
    _game.won = null;
}

function showWinnerScreen() {
    launchPopup("You won!", `assets/media/${_game.playerSkin}-victory.png`);
}

function showLoserScreen() {
    launchPopup("You lost!", `assets/media/${_game.playerSkin}-defeat.png`);
}

function showDrawScreen() {
    launchPopup("Draw!", `assets/media/draw.png`);
}

function updateFooterButton(content, action, target = null, addClass = null) {
    const $button = document.querySelector(`#game-board footer button`);
    $button.innerHTML = content;
    $button.dataset.action = action;
    setButtonTarget($button, target);

    if (addClass) {
        $button.classList.add(addClass);
    }
}

function setButtonTarget($button, target) {
    if (target) {
        $button.dataset.target = target;
    } else {
        $button.removeAttribute("data-target");
    }
}

function launchPopup(message, src) {
    const $popup = document.querySelector('#popup');
    $popup.innerHTML = '';
    addHeaderToPopup(message, $popup);
    addImageToPopup(src, $popup);
    show('popup');
}

function addHeaderToPopup(message, $popup) {
    const $header = document.createElement('h1');
    $header.innerHTML = message;
    $popup.insertAdjacentElement('beforeend', $header);
}

function addImageToPopup(src, $popup) {
    const $image = document.createElement('img');
    $image.src = src;
    $popup.insertAdjacentElement('beforeend', $image);
}
