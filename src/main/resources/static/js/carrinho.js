vercarrinho()
sessionStorage.setItem('prds', '')
function vercarrinho(){
    document.getElementById('listacarrinho').innerHTML = ''
    if(sessionStorage.getItem('loged') == 'true'){
        console.log('foi')
        obj = JSON.parse(sessionStorage.getItem('cart'))
        txt=''
        total =0.0
        produtos = ''
        for(var i in obj){
            console.log(obj[i])
            const ajax = new XMLHttpRequest();
            ajax.withCredentials = true;
            ajax.open("GET", "http://localhost:8080/usuario/buscarprd/"+obj[i]);
            ajax.setRequestHeader("Authorization", sessionStorage.getItem('token'));
            ajax.send();
            ajax.onload = function(){
                prd = JSON.parse(this.responseText)
                produtos += prd.nome + ', '
                txt += "<tr><td>"+prd.nome+"</td>"
                txt += "<td>"+prd.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })+" </td>"
                txt += "<td><input type='button' value='Apagar' onclick='apagarprd("+i+")' class='btn btn-danger'></td></tr>"
                total += prd.valor
                msg = "'Olá gostaria de compra ("+produtos+") Com valor total de: "+total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
                document.getElementById('btnpg').innerHTML = '<input id="valorpagar" onclick="wtts('+(msg+="'")+')" type="button" value="Pagar '+total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })+'" class="btn btn-success">'
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
    window.location.href = 
    console.log(total , x)
}
function wtts(msg){
    let number = '5561999766526'
    // o texto ou algo vindo de um <textarea> ou <input> por exemplo
    // montar o link (número e texto) (web)
    target = `https://api.whatsapp.com/send?phone=${encodeURIComponent(number)}&text=${encodeURIComponent(msg)}`
    window.location.href = target;
    return target
}