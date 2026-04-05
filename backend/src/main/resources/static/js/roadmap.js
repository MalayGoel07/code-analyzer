document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("roadmapBuilderForm");
    const output = document.getElementById("roadmapOutput");
    const resourceCards = document.getElementById("resourceCards");

    if (!form || !output || !resourceCards) {
        return;
    }

    const getLanguageResource = (language) => {
        switch (language) {
            case "Python":
                return {
                    title: "Python Foundation",
                    source: "Bro Code",
                    detail: "Use Python basics, syntax practice, small projects, and problem solving.",
                    tag: "Python"
                };
            case "Java":
                return {
                    title: "Java Foundation",
                    source: "Bro Code",
                    detail: "Focus on OOP, collections, classes, and writing small console applications.",
                    tag: "Java"
                };
            case "JavaScript":
                return {
                    title: "JavaScript Foundation",
                    source: "Bro Code",
                    detail: "Build comfort with JS basics, DOM concepts, and logic building.",
                    tag: "JavaScript"
                };
            default:
                return {
                    title: "Programming Foundation",
                    source: "Bro Code",
                    detail: "Learn one language deeply and practice small projects consistently.",
                    tag: "Programming"
                };
        }
    };

    const getSpecializationResource = (specialization) => {
        if (specialization === "DSA") {
            return {
                title: "DSA Practice",
                source: "Greg Hogg and Gate Smashers",
                detail: "Use Greg Hogg for interview-style problem solving and Gate Smashers for concept clarity.",
                tag: "DSA"
            };
        } else if (specialization === "ML") {
            return {
                title: "Machine Learning Path",
                source: "ML for Everybody by freeCodeCamp",
                detail: "Start with intuition, workflow, and core ML ideas before jumping to advanced models.",
                tag: "ML"
            };
        } else if (specialization === "DL") {
            return {
                title: "Deep Learning Path",
                source: "Professor Bryce",
                detail: "Use it to understand neural networks, training flow, and deep learning fundamentals.",
                tag: "DL"
            };
        }

        return {
            title: "Web Development Practice",
            source: "Bro Code",
            detail: "Use HTML, CSS, JavaScript, and simple projects to strengthen frontend flow.",
            tag: "Web"
        };
    };

    const getYearPlan = (year, specialization) => {
        if (year === "1st Year") {
            return {
                focus: "Build the basics and develop regular coding habits.",
                steps: [
                    "Learn programming fundamentals and write simple programs every week.",
                    "Start DSA slowly with arrays, strings, loops, and basic problem solving.",
                    specialization === "ML" || specialization === "DL"
                        ? "Strengthen maths basics like linear algebra, probability, and functions."
                        : "Build one mini project to get comfortable with applying your main language."
                ]
            };
        } else if (year === "2nd Year") {
            return {
                focus: "Move from basics into structured learning and deeper practice.",
                steps: [
                    "Practice DSA consistently with stacks, queues, linked lists, trees, and recursion.",
                    "Build 2 to 3 projects that match your language and interest.",
                    specialization === "Web Development"
                        ? "Learn frontend structure and simple backend concepts."
                        : "Start the core specialization roadmap and make notes from each learning resource."
                ]
            };
        } else if (year === "3rd Year") {
            return {
                focus: "Shift toward interview preparation, larger projects, and specialization depth.",
                steps: [
                    "Solve medium-level DSA questions regularly and revise previous topics.",
                    "Build one strong portfolio project with clear features and documentation.",
                    specialization === "ML" || specialization === "DL"
                        ? "Work with datasets, model building, and evaluation on small real tasks."
                        : "Prepare for internships with project demos and communication practice."
                ]
            };
        }

        return {
            focus: "Prioritize placement readiness, revision, and strong execution.",
            steps: [
                "Revise DSA patterns, common interview questions, and key CS fundamentals.",
                "Polish resume-worthy projects and prepare a clear explanation for each one.",
                specialization === "ML" || specialization === "DL"
                    ? "Review models, project results, and deployment basics."
                    : "Focus on interviews, aptitude rounds, and practical coding confidence."
            ]
        };
    };

    const renderRoadmap = (year, language, specialization) => {
        const yearPlan = getYearPlan(year, specialization);
        const languageResource = getLanguageResource(language);
        const specializationResource = getSpecializationResource(specialization);

        output.innerHTML = `
            <div class="roadmap-summary">
                <strong>${year}</strong> roadmap for <strong>${language}</strong> with interest in <strong>${specialization}</strong>.
                Main focus: ${yearPlan.focus}
            </div>
            <div class="roadmap-grid">
                <article class="roadmap-block">
                    <h3>Year Focus</h3>
                    <ul>
                        ${yearPlan.steps.map((step) => `<li>${step}</li>`).join("")}
                    </ul>
                </article>
                <article class="roadmap-block">
                    <h3>Main Language Plan</h3>
                    <ul>
                        <li>Spend regular time learning ${language} syntax and core concepts.</li>
                        <li>Use ${languageResource.source} to practice with beginner-friendly examples.</li>
                        <li>Create small projects so the language becomes your default tool.</li>
                    </ul>
                </article>
                <article class="roadmap-block">
                    <h3>Specialization Plan</h3>
                    <ul>
                        <li>Give fixed weekly hours to ${specialization} instead of random study.</li>
                        <li>Follow ${specializationResource.source} as the main starter resource.</li>
                        <li>Write short notes and apply the ideas in one small practice project.</li>
                    </ul>
                </article>
            </div>
        `;

        resourceCards.innerHTML = `
            <article class="resource-card">
                <h3>DSA</h3>
                <p>Greg Hogg and Gate Smashers for concept building and practice flow.</p>
                <span class="tag">Core Prep</span>
            </article>
            <article class="resource-card">
                <h3>${languageResource.title}</h3>
                <p>${languageResource.source}: ${languageResource.detail}</p>
                <span class="tag">${languageResource.tag}</span>
            </article>
            <article class="resource-card">
                <h3>${specializationResource.title}</h3>
                <p>${specializationResource.source}: ${specializationResource.detail}</p>
                <span class="tag">${specializationResource.tag}</span>
            </article>
        `;
    };

    form.addEventListener("submit", (event) => {
        event.preventDefault();

        const formData = new FormData(form);
        const year = formData.get("year");
        const language = formData.get("language");
        const specialization = formData.get("specialization");

        renderRoadmap(year, language, specialization);
    });
});
