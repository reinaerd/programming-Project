"use strict";

const _STATISTICS = {
    'mario': {
        'won': 0,
        'lost': 0,
        'played': 0,
        'avgtime': 0,
        'fastest': 0
    },
    'sonic': {
        'won': 0,
        'lost': 0,
        'played': 0,
        'avgtime': 0,
        'fastest': 0
    },
    'general': {
        'original': {
            'ratio': 0,
            'won': 0,
            'lost': 0
        },
        'duel': {
            'ratio': 0,
            'won': 0,
            'lost': 0
        },
        'infiltrator': {
            'ratio': 0,
            'won': 0,
            'lost': 0
        }
    }
};

const _TIME = {
    h: 0,
    m: 0,
    s: 0
};

let _timeRunning = false;

function updateStatistics() {
    const stats = getStatistics();

    incrementPlayedGames(stats);
    incrementWinsOrLossesInTeam(stats);
    incrementWinsOrLossesInGeneral(stats);
    changeAverageTimeInGame(stats);
    changeQuickestGameIfNeeded(stats);

    setStatistics(stats);
    showStatistics();
}

function getStatistics() {
    if (localStorage.getItem("statistics")) {
        return JSON.parse(localStorage.getItem("statistics"));
    } else {
        return _STATISTICS;
    }
}

function setStatistics(stats) {
    localStorage.setItem("statistics", JSON.stringify(stats));
}

function incrementPlayedGames(stats) {
    const $gamesPlayed = document.querySelector(`#${_game.playerSkin}-games-played`);

    stats[`${_game.playerSkin}`].played++;
    const newValue = stats[`${_game.playerSkin}`].played;

    $gamesPlayed.innerHTML = newValue.toString();

}

function incrementWinsOrLossesInTeam(stats) {
    if (_game.won === "winner") {
        stats[`${_game.playerSkin}`].won++;
    } else if (_game.won === "draw" || _game.won === "loser") {
        stats[`${_game.playerSkin}`].lost++;
    }
}

function incrementWinsOrLossesInGeneral(stats) {
    const $winOrLosses = document.querySelector(`#${_game.version}-${(_game.won === "winner") ? "won" : "lost"}`);
    let gamesWonOrLost;

    if (_game.won === "winner") {
        stats.general[`${_game.version}`].won++;
        gamesWonOrLost = stats.general[`${_game.version}`].won;
    } else if (_game.won === "draw" || _game.won === "loser") {
        stats.general[`${_game.version}`].lost++;
        gamesWonOrLost = stats.general[`${_game.version}`].lost;
    }

    if (gamesWonOrLost) {
        $winOrLosses.innerHTML = gamesWonOrLost.toString();
    }
}



function changeAverageTimeInGame(stats) {
    if (stats[_game.playerSkin].played !== 0) {
        stats[_game.playerSkin].avgtime = getAverageTime(stats);
    }
    else {
        stats[_game.playerSkin].avgtime = "00h00m00s";
    }
}

function changeQuickestGameIfNeeded(stats) {
    if (stats[`${_game.playerSkin}`].played !== 0) {
        stats[`${_game.playerSkin}`].fastest = getFastestTime();
    }
    else {
        stats[`${_game.playerSkin}`].fastest = "00h00m00s";
    }
}

function getWinLoseRatio(won, lost) {
    if (lost === 0) {
        return parseFloat(won).toFixed(2);
    } else {
        const ratio = (won / lost).toFixed(2);
        return ratio.toString();
    }
}

function startTimer() {
    _timeRunning = true;
    countTime();
}

function stopTimer() {
    _timeRunning = false;
    saveCurrentTime();
    _TIME["h"] = 0;
    _TIME["m"] = 0;
    _TIME["s"] = 0;
}

function countTime() {
    if (_timeRunning === true) {
        _TIME["s"]++;

        if (_TIME["s"] === 60) {
            _TIME["m"]++;
            _TIME["s"] = 0;
        }

        if (_TIME["m"] === 60) {
            _TIME["h"]++;
            _TIME["m"] = 0;
            _TIME["s"] = 0;
        }

        setTimeout(countTime, 1000);
    }
}

function saveCurrentTime() {
    saveFastestTime();
    saveTotalTime();
}

function saveFastestTime() {
    const fastestTimeForSkin = `fastTime${_game.playerSkin}`;

    if (localStorage.getItem(fastestTimeForSkin)) {
        if (currentTimeInSeconds() < JSON.parse(localStorage.getItem(fastestTimeForSkin))) {
            localStorage.setItem(fastestTimeForSkin, currentTimeInSeconds());
        }
    } else {
        localStorage.setItem(fastestTimeForSkin, currentTimeInSeconds());
    }
}

function saveTotalTime() {
    const totalTimeForSkin = `totalTime${_game.playerSkin}`;

    if(localStorage.getItem(totalTimeForSkin)) {
        const newTotalTime = JSON.parse(localStorage.getItem(totalTimeForSkin)) + currentTimeInSeconds();
        localStorage.setItem(totalTimeForSkin, newTotalTime);
    } else {
        localStorage.setItem(totalTimeForSkin, currentTimeInSeconds());
    }
}

function currentTimeInSeconds() {
    return _TIME["h"] * 3600 + _TIME["m"] * 60 + _TIME["s"];
}

function getAverageTime(stats) {
    if (localStorage.getItem(`totalTime${_game.playerSkin}`) != null) {
        return Math.floor(JSON.parse(localStorage.getItem(`totalTime${_game.playerSkin}`)) / stats[_game.playerSkin].played);
    } else {
        return 0;
    }
}

function getFastestTime() {
    return JSON.parse(localStorage.getItem(`fastTime${_game.playerSkin}`));

}

function timeToString(totalSeconds) {
    const totalSecondsWithoutHours = totalSeconds % 3600;

    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor(totalSecondsWithoutHours / 60);
    const seconds = totalSecondsWithoutHours % 60;

    return timeWithLeadingZeros(hours, minutes, seconds);
}

function timeWithLeadingZeros(hours, minutes, seconds) {
    const h = (hours < 10) ? "0" + hours : hours;
    const m = (minutes < 10) ? "0" + minutes : minutes;
    const s = (seconds < 10) ? "0" + seconds : seconds;

    return h + "h" + m + "m" + s + "s";
}

function showStatistics() {
    const stats = getStatistics();
    insertTeamStats(stats.mario, "mario");
    insertTeamStats(stats.sonic, "sonic");
    insertGeneralStats(stats.general.original, "original");
    insertGeneralStats(stats.general.duel, "duel");
    insertGeneralStats(stats.general.infiltrator, "infiltrator");
}

function insertTeamStats(stats, skin) {
    document.querySelector(`#${skin}-win-lose`).innerHTML = getWinLoseRatio(stats.won, stats.lost);
    document.querySelector(`#${skin}-games-played`).innerHTML = stats.played;
    document.querySelector(`#${skin}-average-time`).innerHTML = timeToString(stats.avgtime);
    document.querySelector(`#${skin}-quickest-game`).innerHTML = timeToString(stats.fastest);
}

function insertGeneralStats(stats, version) {
    document.querySelector(`#${version}-won`).innerHTML = stats.won;
    document.querySelector(`#${version}-lost`).innerHTML = stats.lost;
    document.querySelector(`#${version}-win-loss`).innerHTML = getWinLoseRatio(stats.won, stats.lost);
}
