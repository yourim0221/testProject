<%@page import="common.util.PagingCommonVO"%>
<%@page import="com.admin.dto.NoticePagingDto"%>
<%@page import="com.admin.dto.NoticeDto"%>
<%@page import="com.sights.dto.SightsDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html>
<html> -->

<%
	//sitemap 관음숙 리스트
	List<SightsDto> mainSiteMapList = new ArrayList<>();
	
	if( request.getAttribute("mainSiteMapList") != null){
		mainSiteMapList = (List<SightsDto>)request.getAttribute("mainSiteMapList");
	}
	
	List<NoticeDto> mainNoticeList = new ArrayList<>();
	NoticePagingDto noticePagingDto = new NoticePagingDto();
	
	if( request.getAttribute("mainNoticeList") != null ){
		mainNoticeList = (List<NoticeDto>)request.getAttribute("mainNoticeList");
	}
	if( request.getAttribute("noticePagingDto") != null ){
		noticePagingDto = (NoticePagingDto)request.getAttribute("noticePagingDto");
	}

%>

<script type="text/javascript" src="/jejuya/resources/js/main/mainsitemap.js"></script>
<link href="/jejuya/resources/css/main/mainsitemap.css" rel="stylesheet">

<div class="mainSiteMapWrap" align="center">
	<div class="mainSiteMapContent">
		<div class="siteMapHeader">
			<ul class="nav nav-tabs" role="tablist">
				<li> <a class="nav-link active" data-toggle="tab" href="#sightsSiteMapItem">인기 여행지</a> </li>
				<li> <a class="nav-link" data-toggle="tab" href="#announceSiteMapItem">공지사항</a> </li>
				<li> <a class="nav-link" data-toggle="tab" href="#shareScheSiteMapItem">일정공유</a> </li>
				<li> <a class="nav-link" data-toggle="tab" href="#tranpSiteMapItem">교통정보</a> </li>
			</ul>
		</div>
		<div class="siteMapBody">
			<!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="sightsSiteMapItem">
                	<div class="titleSiteMapItem">
                		<h2>제주도 추천 여행지</h2>
                    </div>
                    <%
                    	for(int i = 1 ; i < 7 ; i++ ){
                    		SightsDto dto = mainSiteMapList.get(i);
                    		%>
                    			<div class="cmp<%=i %>SiteMapItem">
                    				<%-- <img alt="" src="<%=dto.getFilename() %>"> --%>
                    				<img alt="" src="/jejuya/upload/<%=dto.getFilename() %>">
                    				<div class="cmp<%=i %>InnerSiteMapItem innerSiteMapItem">
                    					<a href="/jejuya/SightsController?command=detailBasic&title=<%=dto.getTitle() %>">
                    						<h3><%=dto.getTitle() %></h3>
                    					</a>
                    				</div>
                    				<div class="cmp<%=i %>BorderSiteMapItem borderSiteMapItem"></div>
                    			</div>
                    		<%
                    	}
                    %>     
                </div>
                <div role="tabpanel" class="tab-pane" id="announceSiteMapItem">                    
                    <div class="cmp1AnnounceSiteMapItem">
                    	<div class="titleSiteMapItem">
	                    	<h2>JEJUYA 에서 알려 드립니다</h2>
	                    </div>
                    	<table class="table table-hover" id="mainNoticeTbl">
                    		<tr class="mainNoticeTblHeader">
                    			<td>번호</td><td>제목</td><td>작성일</td>
                    		</tr>
                    		<%
                   				for(NoticeDto d : mainNoticeList){
                   					%>
                   						<tr class="mainNoticeTblRow">
                   							<td><%=d.getSeq() %></td>
                   							<td><%=d.getTitle() %></td>
                   							<td><%=d.getWdate().toLocaleString().substring(0,12) %></td>
                   						</tr>
                   					<%
                   				}
                   			%>			
                   			<tr class="mainNoticeTblContentBottom">
                   			</tr>				
						</table>
						<div class="mainNoticeTblFooter" align="center">
							<table>
							<tr class="mainNoticeTblNav">
								<td colspan="3">
									<input type="hidden" id="totalNavItemSize" value="<%=noticePagingDto.getPageSize() %>">
									<ul class="pagination" id="noticePagingNav">
										<li class="page-item page-prev"><a class="page-link"><i class="fas fa-angle-left"></i></a></li>
										<%
											for(int i = noticePagingDto.getNavStartNum() ; i < noticePagingDto.getNavStartNum()+5 ; i++ ){
												%>
													<li class="page-item page-nav-item"><a class="page-link"><%=i %></a></li>
												<%												
											}
										%>
										<li class="page-item page-next"><a class="page-link"><i class="fas fa-angle-right"></i></a></li>
									</ul>
								</td>								
							</tr>
							</table>
						</div>
                    </div>
                    
                </div>
                <div role="tabpanel" class="tab-pane" id="shareScheSiteMapItem">
                    <div class="cmp1ShareSiteMapItem">
                    	<div class="titleSiteMapItem">
                    		<h2>준비 중입니다.</h2>
                    	</div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="tranpSiteMapItem">
                    <div class="cmp1TranspSiteMapItem" align="center">
                  		<div class="titleSiteMapItem">
                    		<h2>제주 여행 교통정보 안내</h2>
                    	</div>                    	
                    	<ul class="trafficIcons">
                    	<%
                    		for(int i = 1 ; i < 9 ; i++ ){
                    			%>
                    				<li class="trafficinfoItemInSiteMap">
                    				<a class="traffic0<%=i%>" target="_blank">
                    				<img alt="" src="/jejuya/upload/trafficinfo/traffic0<%=i%>.jpg">
                    				</a>
                    				</li>
                    			<%
                    		}
                    	%>
                    	</ul>
                    </div>                    
                </div>
            </div>
		</div>
	</div>	
</div>

<div class="">

</div>

<!-- Modal -->
<div class="modal" id="noticeDetailModal" role="dialog">
  <div class="modal-dialog">
  
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body" id="noticeModalBody">
        <div id="noticeModalBodyTitle">제목</div>
        <hr>
        <div id="noticeModalBodyContent"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
    
  </div>
</div>

<!-- </html> -->