import 'bootstrap/dist/css/bootstrap.css'
import jokes from "./jokes";

const allJokes = jokes.getJokes().map(joke => "<li>" + joke + "</li>");
document.getElementById("jokes").innerHTML = allJokes.join("");


document.getElementById("jokeButton").addEventListener("click", findJoke);

function findJoke() {
    const jokeID = document.getElementById("joke").value - 1;
    const jokeText = jokes.getJokeById(jokeID);
    document.getElementById("jo").innerHTML = jokeText;
}

document.getElementById("addJoke").addEventListener("click", addJoke);

function addJoke() {

    let newJoke = document.getElementById("add").value;
    jokes.addJoke(newJoke);
}


document.getElementById("getJokeFetch").addEventListener("click", function(evt) {
    evt.preventDefault();
    getJokeByFetching("getJoke");
});

setInterval(getJokeByFetching("getJokeYellow"), 1000 * 60 * 60);

function getJokeByFetching(location) {

    let temp = fetch('https://studypoints.info/jokes/api/jokes/period/hour')
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            document.getElementById(location).innerHTML = data.joke;

        });
}



//ASSIGNMENT 3   ###################################

document.getElementById("north").addEventListener("click", north);
document.getElementById("south").addEventListener("click", south);
document.getElementById("west").addEventListener("click", west);
document.getElementById("east").addEventListener("click", east);


function north(evt) {
    evt.preventDefault();
    let messageNorth = 'Nord';
    document.getElementById("choose").innerHTML = messageNorth;
};

function south(evt) {
    evt.preventDefault();
    let message = 'Syd';
    document.getElementById("choose").innerHTML = message;
};

function east(evt) {
    evt.preventDefault();
    let message = 'Øst';
    document.getElementById("choose").innerHTML = message;
};

function west(evt) {
    evt.preventDefault();
    let message = 'Vest';
    document.getElementById("choose").innerHTML = message;
};



//ASSIGNMENT 4  ######################################

//Make a useras table
getAllUsers();

function getAllUsers() {
    let url = "http://localhost:3333/api/users/";
    fetch(url)
        .then(res => res.json())
        .then(data => {
            console.log("data", data);

            var header = "<tr><th>ID</th><th>Age</th><th>Name</th><th>Gender</th><th>Email</th></tr>";
            var tableBody = data.map(user => {
                return "<tr>" +
                    "<td>" + user.id + "</td>" +
                    "<td>" + user.age + "</td>" +
                    "<td>" + user.name + "</td>" +
                    "<td>" + user.gender + "</td>" +
                    "<td>" + user.email + "</td>" +
                    "</tr>";
            });


            document.getElementById("myTable").innerHTML = "<table border=1>" + header + tableBody + "</table>";

        });
}



//Find person by ID

document.getElementById("personButton").addEventListener("click", getUserByID);

function getUserByID(evt) {
    evt.preventDefault();

    let url = "http://localhost:3333/api/users/";
    fetch(url)
        .then(res => res.json())
        .then(data => {

            var personSearch = document.getElementById("personText").value;

            var personIdMatch = data.filter(person => person.id == personSearch);
            console.log(personIdMatch);
            if (personIdMatch.length > 0) {
                var result = personIdMatch[0];
                document.getElementById("singleUser").innerHTML = "Age: " + result.age + " Name: " + result.name + " Gender: " + result.gender + " Email: " + result.email;
                "<br><br><br>";
            } else {
                document.getElementById("singleUser").innerHTML = "No match found";
            }
        });
}


//Add Person With ErrorHandling

document.getElementById("addButton").addEventListener("click", addPerson);

function addPerson(evt) {
    evt.preventDefault();

    let agec = document.getElementById("addAge").value;
    let namec = document.getElementById("addName").value;
    let genderc = document.getElementById("addGender").value;
    let emailc = document.getElementById("addEmail").value;


    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            age: agec,
            name: namec,
            gender: genderc,
            email: emailc
        })
    }

    fetch("http://localhost:3333/api/users/", options)
        .then(res => res.json())
        .then(data => {
            if (data.status) {
                console.log(data.msg);
                document.getElementById("errorAdd").innerHTML = data.msg;
            } else {
                document.getElementById("errorAdd").innerHTML = '<br>';
            }
        });

    getAllUsers();

};





//Edit person with ErrorHandling


document.getElementById("editButton").addEventListener("click", editPerson);

function editPerson(evt) {
    evt.preventDefault();

    let id = document.getElementById("editId").value;
    let agec = document.getElementById("editAge").value;
    let namec = document.getElementById("editName").value;
    let genderc = document.getElementById("editGender").value;
    let emailc = document.getElementById("editEmail").value;


    let options = {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "age": agec,
            "name": namec,
            "gender": genderc,
            "email": emailc
        })
    }

    fetch("http://localhost:3333/api/users/" + id, options)
        .then(res => res.json())
        .then(data => {
            if (data.status) {
                console.log(data.msg);
                document.getElementById("errorEdit").innerHTML = data.msg;
            } else {
                document.getElementById("errorEdit").innerHTML = '<br>';
            }
        });

    getAllUsers();

};


//Delete Person With ErrorHandling

document.getElementById("deleteButton").addEventListener("click", deletePerson);

function deletePerson(evt) {
    evt.preventDefault();

    let ids = document.getElementById("deleteId").value;

    let options = {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json'
        }
    }

    fetch("http://localhost:3333/api/users/" + ids, options)

    .then(res => res.json())
        .then(data => {
            if (data.status) {
                console.log(data.msg);
                document.getElementById("errorDelete").innerHTML = data.msg;
            } else {
                document.getElementById("errorDelete").innerHTML = '<br>';
            }
        });

    getAllUsers();
};