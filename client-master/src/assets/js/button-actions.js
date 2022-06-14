"use strict";

//
// Dynamically execute functions based on the name of the data-action of a button
// https://www.sitepoint.com/call-javascript-function-string-without-using-eval/
//
// Example:
// A button with 'data-action=setVersion', will run the function 'setVersion' and pass the dataset as argument
//
function handleButtonActions(e) {
    if (e.target.hasAttribute('data-action')) {
        window[e.target.dataset.action].apply(null, [e.target.dataset]);
    }
}

function setVersion(dataset) {
    _game.version = dataset.version;
    showTeamScreen();
}

function setSkin(dataset) {
    _game.playerSkin = dataset.skin;
    _game.enemySkin = (_game.playerSkin === _SKIN_SONIC) ? _SKIN_MARIO : _SKIN_SONIC;
    showPresetScreen();
}

function setPreset() {
    validateAllPiecesAreDeployed();
    showLobbyScreen();
}

function savePreset() {
    localStorage.setItem(_game.version, JSON.stringify(getBoard()));
    setMessage('Preset saved');
}

function loadPreset() {
    const pawns = JSON.parse(localStorage.getItem(_game.version));

    if (pawns) {
        refreshBoardWith(pawns);
        setMessage('Preset loaded');
    } else {
        setMessage(`You don't have any preset saved for ${_game.version}`);
    }
}

function setLobby() {
    showPlayingScreen();
    const roomId = document.querySelector('#room-id').value;
    fetchRoomAndWaitForSecondPlayer(roomId ? roomId : "room");
}

// Because SonarLint keeps reporting FALSE positives on this "Unused Functions"...
function shutUpSonarLint() {
    if (config.shutUpSonarLint === false) {
        setVersion([]);
        setSkin([]);
        setPreset();
        savePreset();
        loadPreset();
        setLobby();
        backToMenu();
    }
}

function forfeitGame() {
    fetchSetLoser(_game.gameId);
    resetGameConfig();
}


function backToMenu() {
    resetGameConfig();
    showVersionScreen();
    setGameStateTo(_STATE_LOBBY);
}

function infiltrate() {
    hide("infiltrate");
    fetchMakeMoveInfiltrateAndWaitTurn();
}

