"use strict";

const _TIME_BETWEEN_API_CALLS_IN_MS = 1000;

//  https://project-i.ti.howest.be/stratego-api-spec/
function fetchFromServer(url, httpVerb, requestBody){
    const options= {};
    options.method = httpVerb;

    options.headers = {};
    options.headers["Content-Type"] = "application/json";

    if(typeof  _game !== 'undefined') {
        options.headers["Authorization"] = "Bearer " + _game.playerToken;
    }
    // Don't forget to add data to the body when needed
    options.body = JSON.stringify(requestBody);

    return fetch(url, options)
        .then(response => {
            if (!response.ok) {
                console.error('%c%s','background-color: red;color: white','! An error occurred while calling the API');
                console.table(response);
            }
            return response.json();
        })
        .then((jsonresponsetoparse) => {
            return jsonresponsetoparse;
        });
}

//  Get all available game version
function fetchVersions() {
    return fetchFromServer(`${config.getAPIUrl()}/versions`, 'GET')
        .then(response => response.versions);
}

//  Get valid pawn types for specific game version
function fetchPieces(version){
    return fetchFromServer(`${config.getAPIUrl()}/versions/${version}`,'GET')
        .then(response => response.pieceCount);
}

//  Join a game with a given board setup
function fetchRoom(roomId) {
    return fetchFromServer(`${config.getAPIUrl()}/games/rooms/${roomId}/configuration`, 'POST', {
        "version": _game.version,
        "startConfiguration": getBoardPreparedForAPI()
    }).then(response => response);
}

//  Get the full moves list
function fetchMoves(gameId) {
    return fetchFromServer(`${config.getAPIUrl()}/games/${gameId}/moves`, 'GET')
        .then(response => response.moves);
}

//  Execute a move
function fetchMakeMove(gameId, playerToken, source, target) {
    return fetchFromServer(`${config.getAPIUrl()}/games/${gameId}/moves`, 'POST', {
        "src": source,
        "tar": target
    }).then(json => json.move);
}

function fetchMakeMoveInfiltrate(gameId, playerToken, source, target, infiltrate) {
    return fetchFromServer(`${config.getAPIUrl()}/games/${gameId}/moves`, 'POST', {
        "src": source,
        "tar": target,
        "infiltrate": infiltrate
    }).then(json => json.move);
}

//  Get the current state of the board
function fetchPawnPositions(gameId) {
    return fetchFromServer(`${config.getAPIUrl()}/games/${gameId}/configuration`, 'GET')
        .then(response => response.configuration);
}

function fetchGetLosers(gameId) {
    return fetchFromServer(
        `${config.getAPIUrl()}/games/${gameId}/losers`,
        'GET'
    ).then(response => response.losers);
}

function fetchSetLoser(gameId) {
    fetchFromServer(
        `${config.getAPIUrl()}/games/${gameId}/losers`,
        'POST'
    ).then(response => response.losers);
}

