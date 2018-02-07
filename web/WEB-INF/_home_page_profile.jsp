<%--personal information card--%>
    <div class="card">
        <img class="card-img-top img-thumbnail img-rounded" src="http://i.pravatar.cc/300?u=${userId}" alt="Card image cap">
        <div class="card-body">
            <h5 class="card-title">${fname} ${lname}
                ${gender==0? '<span class="fa fa-venus" style="color:hotpink"></span>'
                :gender==1?'<span class="fa fa-mars" style="color:DEEPSKYBLUE"></span>'
                :'<span class="fa fa-2x fa-venus-mars fa-gradient"></span>'}
            </h5>
            <p class="card-text">${description}</p>
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
