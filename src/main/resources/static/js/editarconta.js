const xhr = new XMLHttpRequest();
buscardados()
function buscardados(){
xhr.open("GET", "http://"+window.location.hostname+":8080/usuario/identificar/"+sessionStorage.getItem('token'));
xhr.setRequestHeader("Authorization", sessionStorage.getItem("token"));
// xhr.setRequestHeader("cookie", "JSESSIONID=552EE52BE8EB819FFD5CE2A68463EC5A");
xhr.send();
xhr.onload = function(){
    console.log(this.responseText)
    const dados = JSON.parse(this.responseText);
    document.getElementById('email').disabled=true;
    document.getElementById('email').value = dados.login;
    document.getElementById('primeironome').disabled=true;
    document.getElementById('primeironome').value = dados.primeiro_nome;
    document.getElementById('sobrenome').disabled=true;
    document.getElementById('sobrenome').value  = dados.sobrenome;
    document.getElementById('cep').disabled=true;
    document.getElementById('cep').value  = dados.cep;
    document.getElementById('endereco').disabled=true;
    document.getElementById('endereco').value  = dados.endereco;
    document.getElementById('numero').disabled=true;
    document.getElementById('numero').value  = dados.numero;
    document.getElementById('cidade').disabled=true;
    document.getElementById('cidade').value  = dados.cidade;
    document.getElementById('estado').disabled=true;
    document.getElementById('estado').value  = dados.estado;
}
}
function editar(){
    login = document.getElementById('email').value
    primeiro_nome=document.getElementById('primeironome').value
    sobrenome=document.getElementById('sobrenome').value
    cep=document.getElementById('cep').value
    endereco=document.getElementById('endereco').value
    numero=document.getElementById('numero').value
    cidade=document.getElementById('cidade').value
    estado=document.getElementById('estado').value
    const editarusuario = JSON.stringify({
        "login":login,
        "primeiro_nome":primeiro_nome,
        "sobrenome":sobrenome,
        "cep":cep,
        "endereco":endereco,
        "numero":numero,
        "cidade":cidade,
        "estado":estado,
    });
    stts = true;
    if(primeiro_nome.length<3){
        document.getElementById('primeironome').className="form-control is-invalid"
        document.getElementById('primeironomes').style = "color: red;"
        document.getElementById('primeironomes').innerHTML="Insira um nome válido!"
        stts = false;
    }
    if(sobrenome.length<3){
        document.getElementById('sobrenome').className="form-control is-invalid"
        document.getElementById('sobrenomes').style = "color: red;"
        document.getElementById('sobrenomes').innerHTML="Insira um sobrenome válido!"
        stts = false;
    }
    if(cep.length<8){
        document.getElementById('cep').className="form-control is-invalid"
        document.getElementById('ceps').style = "color: red;"
        document.getElementById('ceps').innerHTML="Insira um CEP válido!"
        stts = false;
    }
    if(endereco.length<1){
        document.getElementById('endereco').className="form-control is-invalid"
        document.getElementById('enderecos').style = "color: red;"
        document.getElementById('enderecos').innerHTML="Insira um endereço válido!"
        stts = false;
    }
    if(numero.length<1){
        document.getElementById('numero').className="form-control is-invalid"
        document.getElementById('numeros').style = "color: red;"
        document.getElementById('numeros').innerHTML="Insira um numero válido!"
        stts = false;
    }
    if(cidade.length<1){
        document.getElementById('cidade').className="form-control is-invalid"
        document.getElementById('cidades').style = "color: red;"
        document.getElementById('cidades').innerHTML="Insira uma cidade válida!"
        stts = false;
    }
    if(estado.length<1){
        document.getElementById('estado').className="form-control is-invalid"
        document.getElementById('estados').style = "color: red;"
        document.getElementById('estados').innerHTML="Insira um estado válido!"
        stts = false;
    }
    //http://"+window.location.hostname+":8080/usuario/editar

    if(stts){

        xhr.open("PUT", "http://"+window.location.hostname+":8080/usuario/editar/"+sessionStorage.getItem("token"));
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", sessionStorage.getItem("token"));
        console.log(editarusuario)
        xhr.send(editarusuario);
        xhr.onload = function(){
            console.log(this.status)
        }
    }
}