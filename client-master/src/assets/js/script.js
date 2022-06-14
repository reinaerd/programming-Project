"use strict";

document.addEventListener('DOMContentLoaded',init);

function init(){
    setGameStateTo(_STATE_LOBBY);

    // Loaders: loading and generating parts of the website
    hideAll();
    show('versions');
    insertBoardFieldsHtml();
    loadGameVersion();
    showStatistics();

    // Bindings: adding listeners that bind js actions to actionable elements of the website
    document.querySelectorAll('button')
        .forEach($button => $button.addEventListener('click', handleButtonClick));


    // Delegates: adding event listeners to the parent of elements that will get deleted
    document.querySelector('#your-team').addEventListener('click', handlePieceClick);
    document.querySelector('#playing-field').addEventListener('click', handleFieldClick);
    document.querySelector('#version-buttons').addEventListener('click', handleButtonClick);
    document.querySelector('#infiltrate').addEventListener('click', handleInfiltratorClick);

    // Other initialisations: code that does not match the previous descriptions;
    shutUpSonarLint();
}

function handleButtonClick(e) {
    e.preventDefault();

    try {
        handleButtonActions(e);
        handleButtonNavigation(e);
    } catch (error) {
        setMessage(error);
    }
}

function handleInfiltratorClick(e) {
    e.preventDefault();

    try {
        handleInfiltratorActions(e);
    } catch (error) {
        setMessage(error);
    }
}
