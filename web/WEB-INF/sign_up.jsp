<%--
  Created by IntelliJ IDEA.
  User: nr56
  Date: 23/01/2018
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="_head.jsp"%>
    <title>Create Account</title>
</head>
<body>

<%@include file="_home_page_logo.jsp"%>
<%@include file="_home_page_menu.jsp"%>


<div class="container">
    <div class="row">
    <div class="col-6 ">
        <div id="box" class="panel panel-default" style= "border-color: lightgray; box-shadow: 1px 3px 4px 5px floralwhite ;" >
            <div style="margin-left: 3%">
                <form action="sign-up?signUp=1" method="post">


                    <h3>Personal Information:</h3>
                    <br><br>
                    <div style="margin-left: 4%">
                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="firstN">First Name:</label>
                            <input class="form-control" type="text" id="firstN" name="fname">
                            <br>
                        </div>
                    </div>

                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="lastN">Last Name:</label>
                            <input class="form-control" type="text" id="lastN" name="lname">
                            <br>
                        </div>
                    </div><br><br>

                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="gnd"><strong>Gender:</strong></label>
                            <select  class="radio-inline" id="gnd" name="gender" >
                                <option name="gender" selected>please select</option>
                                <option value="0" name="gender">Female</option>
                                <option value="1" name="gender">Male</option>
                                <option value="2" name="gender">Other</option>
                            </select>
                            <br><br><br>
                        </div>
                    </div>


                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="DOB">Date Of Birth</label>
                            <input class="form-control" type="date" id="DOB" value="dob">
                            <br><br>
                        </div>
                    </div>
                    </div>



                    <h3>Account Information:</h3>
                    <br><br>
                    <div style="margin-left: 4%">
                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="usr">User Name:  </label>
                            <input class="form-control" type="text" id="usr" name="userName" required >
                            <br><br>
                        </div>
                    </div>


                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="emil">Email:   </label>
                            <input class="form-control" type="email" id="emil" name="email" required>
                            <br><br>
                        </div>
                    </div>


                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="passrd">Password:  </label>
                            <input class="form-control" type="password" id="passrd" name="password" required>
                            <br><br>
                        </div>
                    </div>


                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="passrd2">Re-type Password:  </label>
                            <input class="form-control" type="password" id="passrd2" name="password" required>
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
                            <input class="form-control" type="text" id="addr" name="address">
                            <br><br>
                        </div>
                    </div>

                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="cty">City:  </label>
                            <input class="form-control" type="text" id="cty" name="city">
                            <br><br>
                        </div>
                    </div>


                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="ste" >State:  </label>
                            <input class="form-control" type="text" id="ste" name="state">
                            <br><br>
                        </div>
                    </div>



                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="cuntry">Country:  </label>
                            <select id="cuntry" name="country">
                                <option value="nz">New Zealand</option>
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
                            <textarea style="overflow: auto; resize: none" name="description" cols="60" rows="8"></textarea>
                            <br><br>
                        </div>
                    </div>
                    </div>

                    <div class="form-inline">
                        <input style="width: 10em; align-content: space-around" class="form-control" type="submit" value="Sign Up!">
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
</html>
