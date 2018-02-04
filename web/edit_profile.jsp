
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="WEB-INF/_head.jsp"%>
    <title>Edit Account</title>
</head>
<body>
<%@include file="WEB-INF/_home_page_menu.jsp"%>

<c:set var = "user" scope = "session" value = "${sessionScope.get('loggedInUser')}"/>
<div class="container">
    <div class="row">
        <div class="col-6 ">
            <div id="box" class="panel panel-default" style= "border-color: lightgray; box-shadow: 1px 3px 4px 5px floralwhite ;" >
                <div style="margin-left: 3%">
                    <form action="sign-up?editProfile=${user.getId()}" method="post" id="edit-form">
                        <h2 style="text-align: center">Edit Your Profile</h2>

                        <h3>Personal Information:</h3>
                        <br><br>
                        <div style="margin-left: 4%">
                            <div class="form-group">
                                <div style="width: 50%">
                                    <label for="firstN">First Name:</label>
                                    <input class="form-control" type="text" id="firstN" name="fname" required value="${user.getFName()}" >
                                    <br>
                                </div>
                            </div>

                            <div class="form-group">
                                <div style="width: 50%">
                                    <label for="lastN">Last Name:</label>
                                    <input class="form-control" type="text" id="lastN" name="lname" required value="${user.getLName()}" >
                                    <br>
                                </div>
                            </div><br><br>

                            <div class="form-group">
                                <div style="width: 50%">
                                    <label for="gnd"><strong>Gender:</strong></label>
                                    <select  class="form-control" required id="gnd" name="gender" >
                                        <c:if test="${user.getGender()==0}" >
                                            <option selected value="0" name="gender">Female</option>
                                            <option value="1" name="gender">Male</option>
                                            <option value="2" name="gender">Other</option>
                                        </c:if>
                                        <c:if test="${user.getGender()==1}" >
                                            <option value="0" name="gender">Female</option>
                                            <option selected value="1" name="gender">Male</option>
                                            <option value="2" name="gender">Other</option>
                                        </c:if>
                                        <c:if test="${user.getGender()==2}" >
                                            <option value="0" name="gender">Female</option>
                                            <option value="1" name="gender">Male</option>
                                            <option selected value="2" name="gender">Other</option>
                                        </c:if>

                                    </select>
                                    <br><br><br>
                                </div>
                            </div>


                            <div class="form-group">
                                <div style="width: 50%">
                                    <label for="DOB">Date Of Birth</label>
                                    <input class="form-control" type="date" required id="DOB" value="${user.getDob()}" name="dob">
                                    <br><br>
                                </div>
                            </div>
                        </div>


                        <h3>Contact Information:</h3>
                        <br><br>
                        <div style="margin-left: 4%">
                            <div class="form-group">
                                <div style="width: 50%">
                                    <label for="addr">Address:  </label>
                                    <input class="form-control" type="text" id="addr" required value="${user.getAddress()}" name="address">
                                    <br><br>
                                </div>
                            </div>

                            <div class="form-group">
                                <div style="width: 50%">
                                    <label for="cty">City:  </label>
                                    <input class="form-control" type="text" id="cty" required value="${user.getCity()}" name="city">
                                    <br><br>
                                </div>
                            </div>


                            <div class="form-group">
                                <div style="width: 50%">
                                    <label for="ste" >State:  </label>
                                    <input class="form-control" type="text" id="ste" required value="${user.getState()}" name="state">
                                    <br><br>
                                </div>
                            </div>



                            <div class="form-group">
                                <div style="width: 40%">
                                    <label for="cuntry">Country:  </label>
                                    <select id="cuntry" required name="country">
                                        <option selected>${user.getCountry()}</option>
                                        <option> Afghanistan </option>
                                        <option> Albania</option>
                                        <option> Algeria</option>
                                        <option> Andorra</option>
                                        <option> Angola</option>
                                        <option> Anguilla</option>
                                        <option> Antigua & Barbuda</option>
                                        <option> Argentina</option>
                                        <option> Armenia</option>
                                        <option> Australia</option>
                                        <option> Austria</option>
                                        <option> Azerbaijan</option>
                                        <option> Bahamas</option>
                                        <option> Bahrain</option>
                                        <option> Bangladesh</option>
                                        <option> Barbados</option>
                                        <option> Belarus</option>
                                        <option> Belgium</option>
                                        <option> Belize</option>
                                        <option> Benin</option>
                                        <option> Bermuda</option>
                                        <option> Bolivia</option>
                                        <option> Botswana</option>
                                        <option> Brazil</option>
                                        <option> Brunei Darussalam</option>
                                        <option> Burkina Faso</option>
                                        <option> Myanmar/Burma</option>
                                        <option> Burundi</option>
                                        <option> Cameroon</option>
                                        <option> Canada</option>
                                        <option> Cape Verde</option>
                                        <option> Cayman Islands</option>
                                        <option> Central African Republic</option>
                                        <option> Chad</option>
                                        <option> Chile</option>
                                        <option> China</option>
                                        <option> Colombia</option>
                                        <option> Comoros</option>
                                        <option> Costa Rica</option>
                                        <option> Croatia</option>
                                        <option> Cuba</option>
                                        <option> Cyprus</option>
                                        <option> Czech Republic</option>
                                        <option> Democratic Republic of the Congo</option>
                                        <option> Denmark</option>
                                        <option> Djibouti</option>
                                        <option> Dominican Republic</option>
                                        <option> Dominica</option>
                                        <option> England</option>
                                        <option> Egypt</option>
                                        <option> El Salvador</option>
                                        <option> Estonia</option>
                                        <option> Fiji</option>
                                        <option> Finland</option>
                                        <option> France</option>
                                        <option> French Guiana</option>
                                        <option> Gabon</option>
                                        <option> Gambia</option>
                                        <option> Georgia</option>
                                        <option> Germany</option>
                                        <option> Ghana</option>
                                        <option> Great Britain</option>
                                        <option> Greece</option>
                                        <option> Grenada</option>
                                        <option> Guadeloupe</option>
                                        <option> Guatemala</option>
                                        <option> Guinea</option>
                                        <option> Guinea-Bissau</option>
                                        <option> Guyana</option>
                                        <option> Haiti</option>
                                        <option> Honduras</option>
                                        <option> Hungary</option>
                                        <option> Iceland</option>
                                        <option> India</option>
                                        <option> Indonesia</option>
                                        <option> Iran</option>
                                        <option> Iraq</option>
                                        <option> Israel and the Occupied Territories</option>
                                        <option> Italy</option>
                                        <option> Ivory Coast (Cote d'Ivoire)</option>
                                        <option> Jamaica</option>
                                        <option> Japan</option>
                                        <option> Jordan</option>
                                        <option> Kazakhstan</option>
                                        <option> Kenya</option>
                                        <option> Kosovo</option>
                                        <option> Kuwait</option>
                                        <option> Kyrgyz Republic (Kyrgyzstan)</option>
                                        <option> Laos</option>
                                        <option> Latvia</option>
                                        <option> Lebanon</option>
                                        <option> Lesotho</option>
                                        <option> Liberia</option>
                                        <option> Libya</option>
                                        <option> Liechtenstein</option>
                                        <option> Lithuania</option>
                                        <option> Luxembourg</option>
                                        <option> Republic of Macedonia</option>
                                        <option> Madagascar</option>
                                        <option> Malawi</option>
                                        <option> Malaysia</option>
                                        <option> Maldives</option>
                                        <option> Mali</option>
                                        <option> Malta</option>
                                        <option> Martinique</option>
                                        <option> Mauritania</option>
                                        <option> Mauritius</option>
                                        <option> Mayotte</option>
                                        <option> Mexico</option>
                                        <option> Moldova, Republic of</option>
                                        <option> Monaco</option>
                                        <option> Mongolia</option>
                                        <option> Montenegro</option>
                                        <option> Montserrat</option>
                                        <option> Morocco</option>
                                        <option> Mozambique</option>
                                        <option> Namibia</option>
                                        <option> Nepal</option>
                                        <option> Netherlands</option>
                                        <option> New Zealand</option>
                                        <option> Nicaragua</option>
                                        <option> Niger</option>
                                        <option> Nigeria</option>
                                        <option> Korea, Democratic Republic of (North Korea)</option>
                                        <option> Norway</option>
                                        <option> Oman</option>
                                        <option> Pacific Islands</option>
                                        <option> Pakistan</option>
                                        <option> Panama</option>
                                        <option> Papua New Guinea</option>
                                        <option> Paraguay</option>
                                        <option> Peru</option>
                                        <option> Philippines</option>
                                        <option> Poland</option>
                                        <option> Portugal</option>
                                        <option> Puerto Rico</option>
                                        <option> Qatar</option>
                                        <option> Reunion</option>
                                        <option> Romania</option>
                                        <option> Russian Federation</option>
                                        <option> Rwanda</option>
                                        <option> Saint Kitts and Nevis</option>
                                        <option> Saint Lucia</option>
                                        <option> Saint Vincent's & Grenadines</option>
                                        <option> Samoa</option>
                                        <option> Sao Tome and Principe</option>
                                        <option> Saudi Arabia</option>
                                        <option> Senegal</option>
                                        <option> Serbia</option>
                                        <option> Seychelles</option>
                                        <option> Sierra Leone</option>
                                        <option> Singapore</option>
                                        <option> Slovak Republic (Slovakia)</option>
                                        <option> Slovenia</option>
                                        <option> Solomon Islands</option>
                                        <option> Somalia</option>
                                        <option> South Africa</option>
                                        <option> Korea, Republic of (South Korea)</option>
                                        <option> South Sudan</option>
                                        <option> Spain</option>
                                        <option> Sri Lanka</option>
                                        <option> Sudan</option>
                                        <option> Suriname</option>
                                        <option> Swaziland</option>
                                        <option> Sweden</option>
                                        <option> Switzerland</option>
                                        <option> Syria</option>
                                        <option> Tajikistan</option>
                                        <option> Tanzania</option>
                                        <option> Thailand</option>
                                        <option> Timor Leste</option>
                                        <option> Togo</option>
                                        <option> Trinidad & Tobago</option>
                                        <option> Tunisia</option>
                                        <option> Turkey</option>
                                        <option> Turkmenistan</option>
                                        <option> Turks & Caicos Islands</option>
                                        <option> Uganda</option>
                                        <option> Ukraine</option>
                                        <option> United Arab Emirates</option>
                                        <option> United States of America (USA)</option>
                                        <option> Uruguay</option>
                                        <option> Uzbekistan</option>
                                        <option> Venezuela</option>
                                        <option> Vietnam</option>
                                        <option> Virgin Islands (UK)</option>
                                        <option> Virgin Islands (US)</option>
                                        <option> Yemen</option>
                                        <option> Zambia</option>
                                        <option> Zimbabwe</option>
                                    </select>
                                    <br><br>
                                </div>
                            </div>

                            <div class="form-group">
                                <div style="width: 50%">
                                    <p><strong>Description:</strong></p>
                                    <textarea style="overflow: auto; resize: none" name="description" required cols="60" rows="8">${user.getDescription()}</textarea>
                                    <br><br>
                                </div>
                            </div>
                        </div>

                        <div class="form-inline">
                            <input style="width: 10em; align-content: space-around" class="form-control" type="submit" value="Update">
                            <input style="width: 10em" class="form-control" type="reset" value="Cancel">
                            <br><br><br><br><br>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
<script>
    $('form#edit-form').on('submit', function(event) {
        event.preventDefault(); // or return false, your choice
        console.log($(this).serialize());
        $.ajax({
            url: $(this).attr('action'),
            type: 'post',
            data: $(this).serialize(),
            success: function(resp, status) {
                // if success, HTML response is expected, so replace current
                if(resp === "success"){
                    swal("Your changes has been made :)");
                    $('#edit-form').reset();
                }
                else if (resp === "error"){
                    swal("SQL error");
                }
            },
            error: function (data, status) {
                swal("Sorry, there is an error on your form!");
            }

        });
    });

</script>
</html>