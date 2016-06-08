/**
 * 
 */
function resource(json){

    obj = JSON && JSON.parse(json) || $.parseJSON(json);	
    alert(obj.count);
}