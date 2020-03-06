
document.getElementById("svg2").addEventListener("click", worldMap);

function worldMap(pointer){
      
        document.getElementById("info").innerHTML = "";
        let countryChars;
        if(pointer.target.id.length !== 2){
            countryChars = pointer.target.parentNode.id;
        } else {
            countryChars = pointer.target.id;
        }
        let url = 'http://restcountries.eu/rest/v1/alpha?codes=' + countryChars;
        console.log(url);
        myFetch(url, getCountryInfo);
    
}

function getCountryInfo(data){
     data = data[0];
    console.log(data);
    console.log(data.name);
    console.log(data.population);
    console.log(data.area);
    let result = "Country: " + data.name + "<br>"
    + "Population: " + data.population + "<br>"
    + "Area: " + data.area + "<br>"
    + "Borders: " + data.borders.join(",");
    document.getElementById("info").innerHTML = result;
}

function myFetch(url, callback){
    fetch(url)
    .then(res => res.json())
    .then(data => callback(data));
}