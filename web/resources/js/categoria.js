function categoriaDel() {
    var ids = [];
    $("input[name='id_del']:checked").each(function () {
        ids.push($(this).val());
    });
    if (ids.length === 0) {
        alert("Advertencia\n\nSeleccione la(s) fila(s) a Retirar");
    } else {
        var exito = confirm('¿Seguro que deseas borrar los registros?');
        if (exito) {
            $.ajax({
                url: "http://localhost:8084/WS-Server/service/categoria/"+ids,
                type: 'DELETE',
                success:function(data){
                    alert("http://localhost:8084/WS-Server/service/categoria/"+ids);
                }
                
            })
        } else {
            alert("Operación cancelada");
        }
    }
}
