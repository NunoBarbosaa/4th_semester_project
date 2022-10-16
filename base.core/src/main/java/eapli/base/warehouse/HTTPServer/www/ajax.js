function buildWarehouse() {
	var request = new XMLHttpRequest();

    var vBoard = document.getElementById("warehouse");

    request.open("PUT", "/length/720", true);
    request.send();
    request.open("PUT", "/width/800", true);
    request.send();
    request.open("PUT", "/square/40", true);
    request.send();
    request.open("PUT", "/docks/20;4;20;4;20;4;%20;14;20;14;20;14;%1;3;1;3;1;3;%1;5;1;5;1;5;%1;13;1;13;1;13;%1;15;1;15;1;15;%", true);
    request.send();
    request.open("PUT", "/aisles/3;5;1;16;1;16;1;5;1;7;1;8;1;12;1;13;1;16;1%4;5;8;16;8;16;9;5;8;9;8;10;8;11;8;12;8;13;8;14;8;16;8%2;5;11;16;11;16;10;5;11;11;11;12;11;16;11%3;5;18;16;18;16;18;5;18;8;18;9;18;12;18;13;18;16;18%", true);
    request.send();
    request.open("PUT", "/agvs/3;1%5;1%13;1%15;1%4;20%14;20%", true);
    request.send();

    request.onload = function() {
        vBoard.innerHTML = this.responseText;
        vBoard.style.color = "black";
        setTimeout(buildWarehouse, 60000);
    };
            
    request.ontimeout = function() {
        vBoard.innerHTML = "Server timeout, still trying ...";
        vBoard.style.color = "red";
        setTimeout(buildWarehouse, 100);
    };

    request.onerror = function() {
        vBoard.innerHTML = "No server reply, still trying ...";
        vBoard.style.color = "red";
        setTimeout(buildWarehouse, 5000);
    };
        
  	request.open("GET", "/warehouse", true);
	request.timeout = 5000;
  	request.send();
}
	

