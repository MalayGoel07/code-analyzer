document.addEventListener("DOMContentLoaded", function () {
    const fileBtn = document.getElementById("fileModeBtn");
    const textBtn = document.getElementById("textModeBtn");
    const uploadSection = document.getElementById("uploadSection");
    const pasteSection = document.getElementById("pasteSection");

    if (!fileBtn || !textBtn || !uploadSection || !pasteSection) {
        return;
    }

    fileBtn.addEventListener("click", function () {
        uploadSection.classList.remove("hidden");
        pasteSection.classList.add("hidden");
    });

    textBtn.addEventListener("click", function () {
        pasteSection.classList.remove("hidden");
        uploadSection.classList.add("hidden");
    });
});
