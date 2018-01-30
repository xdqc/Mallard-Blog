function addFileInput(){
    var files=document.getElementById('files');
    var newButtom = document.createElement("input");
    newButtom.setAttribute("type","file");
    newButtom.setAttribute("name","file");
    files.appendChild(newButtom);
    files.appendChild(document.createElement("br"));
}