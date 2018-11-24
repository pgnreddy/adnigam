<c:set var="searchobj" value="${requestScope.searchObj}"/>
<tr>
<td colspan="5"><table border="0" cellpadding="2" cellspacing="0" width="100%">
<tbody><tr>
<td class="smallText" valign="top">Displaying <b>${searchobj.start+1}</b> to <b>
        <c:choose>
            <c:when test="${searchobj.count<=searchobj.end+1}">${searchobj.count}</c:when>
            <c:otherwise>${searchobj.end+1}</c:otherwise>
        </c:choose>
        
</b> (of <b>${searchobj.count}</b> ${paginationItem})</td>
<td class="smallText" align="right">
<form name="pages" action="${paginationUrl}" method="get">
<c:choose>
    <c:when test="${searchobj.page>1}">
        <a href="${paginationUrl}&page=${searchobj.page-1}" class="splitPageLink">&lt;&lt;</a>
    </c:when>
    <c:otherwise>
        &lt;&lt;
    </c:otherwise>
</c:choose>                        

&nbsp;&nbsp;Page 
<select name="page" onchange="this.form.submit();"> 
<c:forEach var="itemnumber" begin="1" end="${searchobj.pageEnd}">
<option value="${itemnumber}" <c:if test="${itemnumber==searchobj.page}">selected="selected"</c:if>>${itemnumber}</option>                                
</c:forEach>
</select> of  ${searchobj.pageEnd}&nbsp;&nbsp;

<c:if test="${not empty param.status}">
<input type="hidden" value="${param.status}" name="status"/>
</c:if>
<c:if test="${not empty param.searchTerm}">
<input type="hidden" value="${param.searchTerm}" name="searchTerm"/>
</c:if>
<c:if test="${not empty param.search}">
<input type="hidden" value="${param.search}" name="search"/>
</c:if>
<c:if test="${not empty param.sortBy}">
<input type="hidden" value="${param.sortBy}" name="sortBy"/>
</c:if>
<c:if test="${not empty param.sortOrder}">
<input type="hidden" value="${param.sortOrder}" name="sortOrder"/>
</c:if>
<c:if test="${not empty param.status}">
<input type="hidden" value="${param.status}" name="status"/>
</c:if>
<c:if test="${not empty param.customerId}">
<input type="hidden" value="${param.customerId}" name="customerId"/>
</c:if>
<c:choose>                                    
    <c:when test="${searchobj.page < searchobj.pageEnd}">
        <a href="${paginationUrl}&page=${searchobj.page+1}" class="splitPageLink">&gt;&gt;</a>
    </c:when>
    <c:otherwise>
        &gt;&gt;
    </c:otherwise>
</c:choose>

</form>
</td>
</tr>
</tbody></table></td>
</tr>