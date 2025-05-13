package main

import (
    "fmt"
    "log"
    "html/template"
    "net/http"
)



func homePage(w http.ResponseWriter, r *http.Request){
    fmt.Fprintf(w, "Welcome to the HomePage!")
    fmt.Println("Endpoint Hit: homePage")
}

func pageIndex(w http.ResponseWriter, r *http.Request){
  
    tmpl, err := template.ParseFiles("XamppDefault/index.html")
    if err != nil {
        http.Error(w, err.Error(), http.StatusInternalServerError)
        return
    }


    //sekalia nyoba send data
    data := struct {
        Judul string
    }{
        Judul: "Coba Golang",
    }

    tmpl.Execute(w, data)


    fmt.Println("Endpoint Hit: index")
}



func handleRequests() {
    http.HandleFunc("/", homePage)
    http.HandleFunc("/checkIndex", pageIndex)
    log.Fatal(http.ListenAndServe(":10000", nil))
}

func main() {
    fs := http.FileServer(http.Dir("XamppDefault/static"))

    http.Handle("/static/", http.StripPrefix("/static/", fs)) // untuk ngambil static file seperti js dan css


    fmt.Println("test")
    handleRequests()
}