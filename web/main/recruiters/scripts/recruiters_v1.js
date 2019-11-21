var RECRUIT = ( function() {

    return {
        
        getProfile: function() {

            $.ajax({

                url: '../../SchoolProfile',
                method: 'GET',
                dataType: 'json',

                success: function(response) {
                    
                    // if time allows, convert all of this to jQuery
                    
                    document.getElementById('fname').value = response.fname;
                    document.getElementById('mname').value = response.mname;
                    document.getElementById('lname').value = response.lname;
                    
                    document.getElementById('address').value = response.address;
                    document.getElementById('address2').value = response.address2;
                    document.getElementById('city').value = response.city;
                    document.getElementById('state').value = response.state;
                    document.getElementById('zip').value = response.zip;
                    
                    document.getElementById('email').value = response.email;
                    
                    document.getElementById('schoolname').value = response.schoolname;
                    document.getElementById('uni_type').value = response.uni_type;
                    document.getElementById('num_students').value = response.num_students;
                    document.getElementById('sat').value = response.sat;
                    document.getElementById('toefl').value = response.toefl;
                    document.getElementById('min_expenses').value = response.min_expenses;
                    document.getElementById('max_expenses').value = response.max_expenses;
                    
                    document.getElementById('teamname').value = response.teamname;
                    
                    // Division
                    var division = document.getElementById('division');
                    var divisions_list = document.createElement('select');
                    division.appendChild(divisions_list);
                    
                    for (var i = 0; i > response.divisions_list; i++){
                        var option = document.createElement('option');
                        option.value = response.divisions_list[i];
                        option.text = response.divisions_list[i];
                        divisions_list.appendChild(option);
                    }
                    
                    // Conference
                    var conference = document.getElementById('conference');
                    var conferences_list = document.createElement('select');
                    conference.appendChild(conferences_list);
                    
                    for (var i = 0; i > response.conferences_list; i++){
                        var option = document.createElement('option');
                        option.value = response.conferences_list[i];
                        option.text = response.conferences_list[i];
                        cinferences_list.appendChild(option);
                    }
                    
                    // Mens Sports
                    var mens = document.getElementById('mens');
                    var mens_list = document.createElement('select');
                    mens.appendChild(mens_list);
                    
                    for (var i = 0; i > response.sports_mens_list; i++){
                        var option = document.createElement('option');
                        option.value = response.sports_mens_list[i];
                        option.text = response.sports_mens_list[i];
                        mens_list.appendChild(option);
                    }
                    
                    // Womens Sports
                    var womens = document.getElementById('womens');
                    var womens_list = document.createElement('select');
                    womens.appendChild(womens_list);
                    
                    for (var i = 0; i > response.sports_womens_list; i++){
                        var option = document.createElement('option');
                        option.value = response.sports_womens_list[i];
                        option.text = response.sports_womens_list[i];
                        womens_list.appendChild(option);
                    }

                }

            });

        },
        
        postProfile: function() {

            // Not yet supported

            return false;

        }
        
    };
    
}());
