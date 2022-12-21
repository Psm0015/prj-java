ajax = new XMLHttpRequest();
function logar(){
    usuario=document.getElementById("login").value;
    senha=document.getElementById("senha").value;
    const ususenh = JSON.stringify({
        "login": usuario,
        "password": senha
    });

    ajax.open("POST", "http://localhost:8080/login");
    ajax.setRequestHeader("Content-Type", "application/json");

    ajax.send(ususenh);
    ajax.onload = function(){
        validacao=document.getElementById("validacao");
        if(this.status == 200){
            console.log(this.responseText);
            validacao.innerHTML = ""
            sessionStorage.setItem('token', this.responseText);
            window.location.href = "usuarios.html";
        }else{
            sessionStorage.setItem('token', '');
            validacao.style = "color: red";
            validacao.innerHTML = "Usuário ou senha incorreto(s)!"
        }
    }
}
function ver(){
    view=document.getElementById("mostrar")
    senhacamp=document.getElementById("senha")
    if (view.innerHTML === '<label class="form-check-label" for="form1Example3"><i class="fa-solid fa-eye"></i> Mostrar Senha </label>'){
        view.innerHTML='<label class="form-check-label" for="form1Example3"><i class="fa-solid fa-eye-slash"></i> Esconder Senha </label>'
        senhacamp.type = 'text'
    } else{
        view.innerHTML='<label class="form-check-label" for="form1Example3"><i class="fa-solid fa-eye"></i> Mostrar Senha </label>'
        senhacamp.type = 'password'
    }
    
}
function cadastrar(){
    valid = true
    senha = document.getElementById('novasenha').value;
    csenha = document.getElementById('confsenha').value;
    login = document.getElementById("novologin").value;
    nome = document.getElementById("novonome").value;
    re = /\S+@\S+\.\S+/;
    if(re.test(login)){
        document.getElementById("novologin").className = "form-control is-valid";
        document.getElementById("invalidlg").innerHTML = ""
    }else{
        document.getElementById("novologin").className = "form-control is-invalid";
        document.getElementById("invalidlg").innerHTML = "Login Inválido"
        valid = false
    }
    if(nome.length>4){
        document.getElementById("novonome").className = "form-control is-valid";
        document.getElementById("invalidnm").innerHTML = ""
    }else{
        document.getElementById("novonome").className = "form-control is-invalid";
        document.getElementById("invalidnm").innerHTML = "Nome Inválido"
        valid = false
    }

    if(senha === csenha){
        document.getElementById("novasenha").className = "form-control is-valid";
        document.getElementById("confsenha").className = "form-control is-valid";
        document.getElementById("invalidpass").innerHTML = "";
    }else{
        document.getElementById("novasenha").className = "form-control is-invalid";
        document.getElementById("confsenha").className = "form-control is-invalid";
        document.getElementById("invalidpass").innerHTML = "As senhas não conferem!";
        valid = false
    }

    if (valid){
        document.getElementById('novasenha').disabled=true;
        document.getElementById('confsenha').disabled=true;
        document.getElementById("novologin").disabled=true;
        document.getElementById("novonome").disabled=true;
        document.getElementById("cadfecha").disabled=true;
        document.getElementById("cadenvia").innerHTML='<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true">';
        document.getElementById("cadenvia").disabled=true;

        const cadastro = JSON.stringify({
            "login": login,
            "nome":nome,
            "password": senha
        })
        ajax.open("POST", "http://localhost:8080/api/usuario/novo");
        ajax.setRequestHeader("Content-Type", "application/json");
        ajax.send(cadastro)
        ajax.onload = function(){
            window.location.href = "confirmarEmail.html";
        }
    }
}