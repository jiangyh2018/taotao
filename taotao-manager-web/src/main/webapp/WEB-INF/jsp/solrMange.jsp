<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false"
     style="padding:10px;">
    <button class="easyui-linkbutton" onclick="importAllItem()">一键导入商品数据到索引库</button>
</div>

<script type="text/javascript">

    function importAllItem() {
        $.post("/searchItem/importall", function (data) {
            if(data.status == 200){
                $.messager.alert('提示','导入成功!');
            }
        });
    }

</script>