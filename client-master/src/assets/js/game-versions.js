"use strict";

function loadGameVersion() {
    const $parent = document.querySelector('#version-buttons');

    fetchVersions()
        .then(versions => {
            versions.forEach(version => {
                $parent.insertAdjacentElement("beforeend", createButtonHtml(version));
                document.querySelector('#versions h2').innerHTML = "Select version";
            });
        });
}

function createButtonHtml(version) {
    const $button = document.createElement('button');

    $button.dataset.current = "versions";
    $button.dataset.target = "skins";
    $button.dataset.version = version;
    $button.dataset.action = "setVersion";
    $button.className = "magnify";
    $button.innerHTML = capitalizeFirstLetter(version);

    return $button;
}

function capitalizeFirstLetter(version) {
    return version.charAt(0).toUpperCase() + version.slice(1);
}
