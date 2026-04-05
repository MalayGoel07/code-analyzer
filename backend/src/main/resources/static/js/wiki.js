window.onload = async function getlanguageinfo() {
    const params = new URLSearchParams(window.location.search);
    const lang = params.get("name");

    document.getElementById("title").innerText = (lang || "").toUpperCase();

    const titlemap = {
        python: "Python_(programming_language)",
        java: "Java_(programming_language)"
    };

    const key = (lang || "").toLowerCase();
    const title = titlemap[key] || lang;
    const safeTitle = encodeURIComponent(title || "");
    const url = `https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=${safeTitle}&format=json&origin=*`;

    const response = await fetch(url);
    const data = await response.json();
    const page = Object.values(data.query.pages)[0];

    if (!page || !page.extract) {
        document.getElementById("output").innerText = "No description found for this language.";
        return;
    }
    document.getElementById("output").innerHTML = page.extract;
};
