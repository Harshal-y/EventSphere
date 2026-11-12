
// ==========================================
// EVENTSPHERE - FRONTEND JAVASCRIPT
// ==========================================

// Backend API URL
const API_BASE = "http://localhost:8080/api";


// HTML ELEMENTS

const grid = document.getElementById("eventGrid");

const searchInput = document.getElementById("searchInput");

const categoryFilter = document.getElementById("categoryFilter");

const dialog = document.getElementById("registerDialog");


// Store all events
let allEvents = [];


// ==========================================
// FORMAT EVENT DATE
// ==========================================

function formatDate(value) {

    return new Date(value).toLocaleString("en-IN", {

        day: "numeric",
        month: "short",
        year: "numeric",

        hour: "numeric",
        minute: "2-digit"

    });

}


// ==========================================
// PREVENT HTML INJECTION
// ==========================================

function safe(value) {

    return String(value ?? "").replace(/[&<>"']/g, ch =>

        ({

            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': "&quot;",
            "'": "&#39;"

        }[ch])

    );

}


// ==========================================
// DISPLAY EVENTS
// ==========================================

function renderEvents() {

    const term = searchInput.value.toLowerCase().trim();

    const category = categoryFilter.value.toLowerCase();


    const filtered = allEvents.filter(event =>

        (!category || event.category.toLowerCase() === category) &&

        [

            event.title,
            event.description,
            event.venue,
            event.city,
            event.category

        ].some(value =>

            String(value ?? "").toLowerCase().includes(term)

        )

    );


    // Update event count

    document.getElementById("eventCount").textContent =

        `${filtered.length} event${filtered.length === 1 ? "" : "s"} found`;


    // If no events found

    if (!filtered.length) {

        grid.innerHTML =

            '<p class="loading">No events match your search. Try another category.</p>';

        return;

    }


    // Generate event cards

    grid.innerHTML = filtered.map(event => `

        <article class="event-card">

            <img
                class="event-image"
                src="${safe(event.imageUrl || "")}"
                alt="${safe(event.title)}"
                onerror="this.style.visibility='hidden'"
            >

            <div class="event-info">

                <span class="tag">
                    ${safe(event.category)}
                </span>

                <h3>
                    ${safe(event.title)}
                </h3>

                <p>
                    ${safe(event.description)}
                </p>

                <div class="event-meta">

                    <span>
                        ▦ ${safe(formatDate(event.eventDate))}
                    </span>

                    <span>
                        ⌖ ${safe(event.venue)}, ${safe(event.city)}
                    </span>

                </div>

                <div class="event-bottom">

                    <span class="price">

                        ${
                            Number(event.price) === 0
                            ? "Free"
                            : "₹" + safe(event.price)
                        }

                    </span>

                    <button
                        class="register-btn"
                        data-event-id="${event.id}"
                    >
                        Register →
                    </button>

                </div>

            </div>

        </article>

    `).join("");

}


// ==========================================
// FETCH EVENTS FROM JAVA BACKEND
// ==========================================

async function loadEvents() {

    try {

        const response = await fetch(`${API_BASE}/events`);

        if (!response.ok) {

            throw new Error("API returned " + response.status);

        }

        allEvents = await response.json();

        renderEvents();

    }

    catch (error) {

        grid.innerHTML = `

            <p class="loading">

                Could not connect to the EventSphere server.

                Start the Java backend and refresh this page.

            </p>

        `;

        document.getElementById("eventCount").textContent =

            "Server unavailable";

        console.error(error);

    }

}


// ==========================================
// OPEN REGISTRATION POPUP
// ==========================================

grid.addEventListener("click", event => {

    const button = event.target.closest("[data-event-id]");

    if (!button) return;


    const selected = allEvents.find(item =>

        item.id === Number(button.dataset.eventId)

    );

    if (!selected) return;


    document.getElementById("eventId").value = selected.id;

    document.getElementById("dialogTitle").textContent = selected.title;

    document.getElementById("registrationResult").textContent = "";

    document.getElementById("registerForm").hidden = false;


    dialog.showModal();

});


// ==========================================
// CLOSE REGISTRATION POPUP
// ==========================================

document.getElementById("closeDialog").addEventListener("click", () => {

    dialog.close();

});


// ==========================================
// SEARCH EVENTS
// ==========================================

searchInput.addEventListener("input", renderEvents);


// ==========================================
// FILTER EVENTS BY CATEGORY
// ==========================================

categoryFilter.addEventListener("change", renderEvents);


// ==========================================
// REGISTER FOR EVENT
// ==========================================

document.getElementById("registerForm").addEventListener("submit", async event => {

    event.preventDefault();


    const result = document.getElementById("registrationResult");

    const button = event.currentTarget.querySelector("button[type=submit]");


    button.disabled = true;

    button.textContent = "Registering...";

    result.textContent = "";


    // Collect registration details

    const payload = {

        eventId: Number(document.getElementById("eventId").value),

        attendeeName: document.getElementById("attendeeName").value.trim(),

        email: document.getElementById("attendeeEmail").value.trim()

    };


    try {

        const response = await fetch(`${API_BASE}/registrations`, {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(payload)

        });


        const data = await response.json();


        if (!response.ok) {

            throw new Error(

                data.message || data.detail || "Registration failed"

            );

        }


        // Hide registration form after success

        document.getElementById("registerForm").hidden = true;


        result.textContent = data.message + " ";


        // Show Telegram group invitation

        if (data.telegramInviteLink) {

            const link = document.createElement("a");

            link.href = data.telegramInviteLink;

            link.target = "_blank";

            link.rel = "noopener noreferrer";

            link.textContent = "Join Telegram group";


            result.appendChild(link);

        }

    }


    catch (error) {

        result.textContent = error.message;

    }


    finally {

        button.disabled = false;

        button.textContent = "Complete registration →";

    }

});


// ==========================================
// INITIALIZE WEBSITE
// ==========================================

loadEvents();
