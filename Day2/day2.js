//1) Using existing functions that takes a callback as an argument

//a Use the filter method: Returns elements containing the letter ‘a’.

let names = ["Lars", "Jan", "Peter", "Bo"];
let char = 'a';
let namesWithA = names.filter(function(element) {
    return element.includes(char);
});
console.log(namesWithA);


//b Use Map method to return an array with reversed names.

let arr = names.map(function(element) {
    let splitText = element.split("");
    let reversedText = splitText.reverse();
    let joiningText = reversedText.join();
    return joiningText;
});

console.log(arr);



console.log('XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX')


//2)   Implement user defined functions that take callbacks as an argument

//a  use myFilter(array, callback)

function myFilter(array, callback) {

    let newNames = [];

    for (var i = 0; i < array.length; i++) {

        if (callback(array[i])) {
            newNames.push(array[i]);
        }
    }
    return newNames;
};

function filterNameWith(name) {
    return name.includes('a');
}
console.log(myFilter(names, filterNameWith));



console.log('############################################');



//b Use myMap(array, callback)

function myMap(array, callback) {

    let newNames = [];

    for (var i = 0; i < array.length; i++) {

        newNames.push(callback(array[i]));

    }
    return newNames;
}

function mapName(name) {
    return name.split('').reverse().join('');
}

console.log(myMap(names, mapName));


console.log("#############################");



//4) Getting really comfortable with filter and map

//a Use map +  callback to map numbers into : result = [4,8,15,21,11];

var numbers = [1, 3, 5, 10, 11];

let manipulatedNumbers = numbers.map(function(element, index, array) {

    if (index + 1 > array.length - 1) {
        return element;
    } else {
        return element + array[index + 1];

    }
});

console.log(manipulatedNumbers);


console.log("#############################");


//b Use map() to create the <a>’s for a navigation set and eventually 
//a string like below (use join() to get the string of <a>’s):

let mapping = names.map((element) => {
    return '<a href=””>' + element + '</a>';

}).join('');

let newMapping = '<nav>' + mapping + '</nav>';
console.log(newMapping);



console.log('################################################################################');


//c Use map()+(join + ..) to create a string, representing a two column table

let tableInfo = [
    { name: "Lars", phone: "1234567" },
    { name: "Peter", phone: "675843" },
    { name: "Jan", phone: "98547" },
    { name: "Bo", phone: "79345" }
];



function makeTable(array) {
    let stringHead = "<tr><th>Name</th><th>Phone</th></tr>";
    let stringData = "";
    array.forEach(element => {
        let temp = "<tr>";
        temp += Object.values(element).map(function(a) {
            return "<td>" + a + "</td>";
        }).join('') + "</tr>";
        stringData += temp;
    });
    return "<table border='1'>" + stringHead + stringData + "</table>";
}

console.log(makeTable(tableInfo));


console.log('##########################################################');


//d Create a single html-file 
function initiate() {

    document.getElementById('links').innerHTML = newMapping;
    document.getElementById('table').innerHTML = makeTable(tableInfo);
}

initiate();

console.log('#########################################################################################');

//e Add a button with a click-handler and use the filter method to find only 
//names containing the letter ‘a’. Update the table to represent the filtered data.

document.getElementById("btn").addEventListener("click", updateList);

function updateList() {

    let tableInfoWithA = tableInfo.filter(({ name }) => name.includes("a"));
    document.getElementById('table').innerHTML = makeTable(tableInfoWithA);


}