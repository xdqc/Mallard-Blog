<%--personal information panel--%>
    <div class="panel profile-panel">
        <img class="panel-img-top img-thumbnail img-rounded" src="http://i.pravatar.cc/300?u=${userId}" alt="panel image cap">
        <div class="panel-body">
            <h5 class="panel-title profile-panel-title">${fname} ${lname}
                ${gender==0? '<span class="fa fa-venus" style="color:hotpink"></span>'
                :gender==1?'<span class="fa fa-mars" style="color:DEEPSKYBLUE"></span>'
                :'<span class="fa fa-2x fa-venus-mars fa-gradient"></span>'}
            </h5>
            <p class="panel-text profile-panel-text">${description}</p>
        </div>
        <dl class="list-group">
            <div class="list-group-item item-list">
                <dt><span class="fa fa-map-marker"></span> Posts:&nbsp;${post_number} </dt>
            </div>
            <div class="list-group-item item-list">
                <dt><span class="fa fa-envelope"></span> From:&nbsp;${country}</dt>
            </div>
            <div class="list-group-item item-list">
                <dt><span class="fa fa-heart"></span> Age:&nbsp; ${age}</dt>
            </div>
        </dl>
    </div>
