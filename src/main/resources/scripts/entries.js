const form = document.querySelector("#form");
const entryList = document.querySelector(".entries");

form.addEventListener("submit", function (e) {
    e.preventDefault();
    // name=John&comment=User comment&email=john%40yahoo.com&date=22/10/2020

    let date = new Date();
    let day = ("0" + date.getDate()).slice(-2);
    let month = ("0" + (date.getMonth() + 1)).slice(-2);
    let year = date.getFullYear();
    let hours = ("0" + date.getHours()).slice(-2);
    let minutes = ("0" + date.getMinutes()).slice(-2);
    let seconds = ("0" + date.getSeconds()).slice(-2);
    let dateTime = [year, month, day].join('-') + " " + hours + ":" + minutes + ":" + seconds;

    const data = `content=${this.content.value}&authorName=${this.authorName.value}&dateAndTime=${dateTime}`;
    console.log(data);

    setEntry(data);
});

function getEntries() {
    fetch("http://localhost:8015/guestbook")
        .then(function (response) {
            return response.json();
        })
        .then(function (entries) {
            displayEntries(entries);
        });
}

function setEntry(data) {
    fetch("http://localhost:8015/guestbook",
        {
            // credentials: 'include',
            mode: "no-cors",
            method: "POST",
            body: data
        })
        .then(function (response) {
            console.log(response);
            alert("Added!");
        });
}

function displayEntries(entries) {
    if (entries) {
        entries.forEach(entry => {
            let node = `
            <div class="entry">
                <textarea class="content-entry" rows="3" cols="23" style="resize: none" readonly>${entry.content}</textarea>
                <p class="author-name-entry">Name: ${entry.authorName}</p>
                <p class="date-and-time-entry">Date: ${entry.dateAndTime}</p>
            </div>
            `;
            entryList.innerHTML += node;
        })
    }
}

getEntries();