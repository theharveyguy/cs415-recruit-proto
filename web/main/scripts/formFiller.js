var easyURL;

var formFiller = ( function(){
    return{
        
        data = [],
        country = $("#country").val(),
        region = $("#region").val(),
        city = $("#city").val(),
        
        fillCountry: function(){
            data = ["country"];
            $.get(easyURL,data,
                  function(response){
                      $("#country").html(response);
                  }
            );
        },
        fillRegion: function(){
            data = ["region", country];
            $.get(easyURL,data,
                  function(response){
                      $("#country").html(response);
                  }
            );
        },
        fillCity: function(){
            data = ["city", region];
            $.get(easyURL,data,
                  function(response){
                      $("#country").html(response);
                  }
            );
        },
        fillSports: function(){
            data = ["sports"];
            $.get(easyURL,data,
                  function(response){
                      $("#country").html(response);
                  }
            );
        }
    };
}());