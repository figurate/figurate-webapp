<% def node = request.getAttribute('contextNode') %>
<h2 style="color:lightgray">
<% 
    def breadcrumb
    breadcrumb = { n ->
        if (!n.parent.isSame(n.session.rootNode)) {
            breadcrumb(n.parent)
        }
        %>&nbsp;&raquo&nbsp;<%
        html.a(href: "/view${n.path - /mn:context\/?/}", style: 'color:gray', n['mn:title'].string)
    }
    breadcrumb(node)
%><form action="/view.html">
    <input type="hidden" name="p" value="<%= node.path%>"/>
    <input type="text" name="c"/>
    <input type="submit" value="Add"/>
</form>

</h2>
