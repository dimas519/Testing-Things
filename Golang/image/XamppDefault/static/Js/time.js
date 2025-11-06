const timeElements=document.getElementsByClassName("time")


fetch("https://www.timeapi.io/api/time/current/zone?timeZone=Asia%2FJakarta")
.then(response => response.json()) // output json atau string ke json
.then(json => setText(json)) // send response body to next then chain


function setText(json){
 timeElements[0].innerHTML=json['dateTime']

 timeElements[0].setAttribute("Waktu",json['dateTime'])
}


