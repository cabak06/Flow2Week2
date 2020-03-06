//5. reduce


//a  Use join to create a single string from all, 
//with names: comma-, space. and  # - separated.

let all = ["Lars", "Peter", "Jan", "Bo"];
let word = ', #';
let addWords = (accumulator, currentValue) => accumulator + word + currentValue;
console.log(all.reduce(addWords));




//b Create a reducer callback that, with reduce(..),  
//will return the sum (105) of all values in numbers

let numbers = [2, 3, 67, 33];
let summing = (accumulator, currentValue) => accumulator + currentValue;
console.log(numbers.reduce(summing, 0));



//c Create a reducer callback that, using the Array’s  reduce() method,  
//will return the average age of all members (25 for the provided array).

var members = [
    { name: "Peter", age: 18 },
    { name: "Jan", age: 35 },
    { name: "Janne", age: 25 },
    { name: "Martin", age: 22 }
];

let total = members.reduce(function(accumulator, currentValue) {
    return accumulator + currentValue.age;
}, 0);
const average = total / members.length;
console.log(average);



//d Create a reduce function that will return a single object like 
//{Clinton: 3, Trump: 4, None: 1 }

const votes = ["Clinton", "Trump", "Clinton", "Clinton", "Trump", "Trump", "Trump", "None"];

const countVotes = votes.reduce((totalVotes, vote) => {
    totalVotes[vote] = (totalVotes[vote] || 0) + 1;
    return totalVotes;
}, {});

console.log(countVotes);



/*
For the rest of the exercises (6 and 7), out teacher (Jon), explained 
in the presence of the whole class about the concept of hoisting.
Basically, hoisting in JS means that all VAR declarations (not LET or CONSTANT)
are hoisted/lifted to the top of their functional/local scope 
(if declared inside a function) or to the top of their global scope 
(if declared outside of a function) regardless of where the actual 
declaration has been made. This is what we mean by “hoisting”.
Therefore, in order to avoid confusion and problems, it is best practice 
to either use let or constant.

He (Jon) didn't dive much into the concept of "this" in exercise 7.. 
and he didnt ask us to dwell too much nor to focus on exercise 7.

*/