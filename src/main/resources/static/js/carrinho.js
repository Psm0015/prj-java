vercarrinho()
sessionStorage.setItem('prds', '')
function vercarrinho(){
    document.getElementById('listacarrinho').innerHTML = ''
    if(sessionStorage.getItem('loged') == 'true'){
        console.log('foi')
        obj = JSON.parse(sessionStorage.getItem('cart'))
        txt=''
        total =0.0
        for(var i in obj){
            console.log(obj[i])
            const ajax = new XMLHttpRequest();
            ajax.withCredentials = true;
            ajax.open("GET", "http://localhost:8080/usuario/buscarprd/"+obj[i]);
            ajax.setRequestHeader("Authorization", sessionStorage.getItem('token'));
            ajax.send();
            ajax.onload = function(){
                produtos += prd.nome + '%0A'
                x = sessionStorage.getItem('prds')
                x += produtos
                x = sessionStorage.setItem('prds', produtos)
                prd = JSON.parse(this.responseText)
                txt += "<tr><td>"+prd.nome+"</td>"
                txt += "<td>"+prd.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })+" </td>"
                txt += "<td><input type='button' value='Apagar' onclick='apagarprd("+i+")' class='btn btn-danger'></td></tr>"
                total += prd.valor
                document.getElementById('valorpagar').value = 'Pagar '+total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
                sessionStorage.setItem('tot',total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }))
                console.log(total)
                document.getElementById('listacarrinho').innerHTML += txt
                txt = ''
            }
        }
    }
}
function apagarprd(id){
    obj = JSON.parse(sessionStorage.getItem('cart'))
    console.log(obj)
    obj.splice(id, 1)
    console.log(obj)
    sessionStorage.setItem('cart', JSON.stringify(obj))
    vercarrinho()
}
function pagar(){
    document.getElementById('valorpagar').value
    total = sessionStorage.getItem('tot')
    x = sessionStorage.getItem('prds')
    // window.location.href = 'https://wa.me/5561999766526?text=Olá%20tudo%20bem,%20Gostaria%20de%20comprar:%0A'
    console.log(total , x)
}