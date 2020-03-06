




/* global fetch */


//Make table with all users Fetching from http://localhost:3333/api/users/";

getAllUsers();

function getAllUsers() {
    //evt.preventDefault();
    console.log("Hello");
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




//Add a perosn to the table
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
    };

    fetch("http://localhost:3333/api/users/", options);

}


//Update the user-table by calling it.
document.getElementById("buttonReload").addEventListener("click", getAllUsers);



