var RECRUIT = ( function() {

    return {
        
        getProfile: function() {

            $.ajax({

                url: '../../AthleteProfile',
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
                    
                    document.getElementById('dob_m').value = response.dob_m;
                    document.getElementById('dob_d').value = response.dob_d;
                    document.getElementById('dob_y').value = response.dob_y;
                    
                    document.getElementById('gender').value = response.gender;
                    
                    document.getElementById('highschool').value = response.highschool;
                    document.getElementById('sat').value = response.sat;
                    document.getElementById('toefl').value = response.toefl;
                    document.getElementById('budget').value = response.budget;
                    
                    document.getElementById('awards').value = response.awards;
                    document.getElementById('social').value = response.social;

                }

            });

        },
        
        postProfile: function() {

            $.ajax({

                url: '../../AthleteProfile',
                method: 'POST',
                data: $('#profileform').serialize(),
                dataType: 'json',

                success: function(response) {
                    
                    var result = Number(response["result"]);
                    
                    if (result === 1) {
                        alert("Profile updated successfully!");
                    }

                }

            });

            return false;

        }
        
    };
    
}());
