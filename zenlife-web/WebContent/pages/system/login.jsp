<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>qifu</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="./tether/tether.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script type="text/javascript" src="./tether/tether.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="./popper-js/umd/popper.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="./jquery/jquery-3.1.1.min.js?ver=${jsVerBuild}"></script>
<link rel="stylesheet" href="./bootstrap-4/css/bootstrap.css?ver=${jsVerBuild}" crossorigin="anonymous">
<link href="./font-awesome/css/font-awesome.min.css?ver=${jsVerBuild}" rel="stylesheet" type="text/css" />
<script src="./bootstrap-4/js/bootstrap.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>
<script src="./bootbox/bootbox.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>

<link rel="stylesheet" href="./toastr/toastr.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script src="./toastr/toastr.min.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>


<style type="text/css">

body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #ffffff;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}

</style>

<script type="text/javascript">

function submitLoginForm() {	
	document.loginForm.submit();
	$('#myPleaseWait').modal('show');	
}

function showRegedit() {
	$('#readmeModal').modal('show');
}

function confirmAcceptLaw() {
	var acceptLaw = $("#acceptLaw").is(':checked');
	if (acceptLaw) {
		window.location = './register.do';
		return;
	}
	toastr.warning( "必需接受條約才可加入會員" );
}

</script>

</head>

<body>

<!-- Modal Start here-->
<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="myPleaseWait" data-keyboard="false" data-backdrop="static">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="mySmallModalLabel">請等待!</h4>
      </div>
      <div class="modal-body">
        <img alt="loading" src="./images/loadingAnimation.gif" border="0">
      </div>
    </div>
  </div>
</div>
<!-- Modal ends Here -->


<!-- Modal Start here, readme modal -->
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="readmeModalLabel" aria-hidden="true" id="readmeModal" data-keyboard="false" data-backdrop="static">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="readmeModalLabel">條文說明</h4>
      </div>
      <div class="modal-body">
		  <div class="form-group">
		    <textarea class="form-control" id="exampleTextarea" rows="25">
若您為未滿二十歲之未成年人，則應請您的父母或監護人閱讀、了解並同意本服務條款之所有內容及其後之修改變更，方得使用本服務。當您使用本服務時，即推定您的父母或監護人已閱讀、了解並同意接受本服務條款之所有內容及其後之修改變更。
隱私權保護政策
本會相當重視隱私權的保護。關於您的會員註冊以及其他特定資料，將依本服務「隱私權保護政策」保護與規範。您了解並同意當您使用本服務時，本服務可依據「隱私權保護政策」之規範進行您個人資料的蒐集與利用，如線上活動及網路調查等。
一般條款
本服務條款構成您與本會就您使用本服務之完整合意，取代您先前與本會間有關本服務所為之任何約定。本服務條款之解釋與適用，以及與本服務條款有關的爭議，除法律另有規定者外，均應依照中華民國法律予以處理，並以台灣台北地方法院為管轄法院。
 
本會未行使或執行本服務條款任何權利或規定，不構成前開權利或規定之棄權。若任何本服務條款規定，經有管轄權之法院認定無效，當事人仍同意法院應努力使當事人於前開規定所表達之真意生效，且本服務條款之其他規定仍應完全有效。
會員的註冊義務及會員帳號、密碼安全
為了能使用本服務，您同意並承諾以下事項：
依本服務註冊流程之提示提供您本人正確及完整的資料，並維持、更新該資料，確保其為正確、最新及完整。
若您提供任何不完整、錯誤或不實的資料，本會有權暫停或終止您的帳號，並拒絕您使用本服務之全部或部分。
 
完成本服務的註冊流程後，您將收到一組帳號及密碼。您同意並承諾以下事項：
此帳號係不可轉讓。
您有責任維持此帳號及密碼的機密安全。
當您的帳號遭到盜用或有其他任何安全問題發生時，您將立即通知本會。
會員使用規範與義務
除了遵守本服務條款之約定外，您同意遵守中華民國相關法規、本服務其他服務條款，並同意不從事以下行為： 您若係中華民國以外之使用者，並同意遵守所屬國家或地域之相關法規。您同意並保證絕不利用本服務從事違法或損害他人權益之行為。
冒用他人名義使用本服務。
侵害他人名譽、隱私權、著作權、智慧財產權及其他權利。
上載、張貼或公布任何不實、誹謗、侮辱、具威脅性、攻擊性、違背公序良俗、引人犯罪或其他不法之文字、圖片或任何形式的檔案。
違反依法律或契約所應負之保密義務。
上載、張貼或公佈任何含有電腦木馬、病毒或任何對電腦軟、硬體產生中斷、破壞或限制功能之程式碼或資料。
從事販賣槍枝、毒品、禁藥、盜版軟體、其他違禁物或其他不法交易行為。
破壞及干擾本服務所提供之各項資料、活動或功能，或以任何方式侵入、試圖侵入、破壞本服務之任何系統，或藉由本服務為任何侵害或破壞行為。
未經同意收集他人電子郵件位址及其他個人資料者。
其他本會有正當理由認為不適當之行為。
 
若您有違反前開事項之情事或之虞，本會有權於通知或未通知之情形下，隨時終止或限制您使用帳號（或其任何部分）或本服務之使用，並將本服務內任何相關「會員內容」加以移除並刪除。您同意若本服務之使用被終止或限制時，本會對您或任何第三人均不承擔責任。
對本服務的貢獻
若您對於本服務提供任何想法、建議或提議（以下稱「貢獻」）並回饋給本會，您了解並同意：
該貢獻並非特定機密或專有資訊，且不侵害他人名譽、隱私權、著作權、智慧財產權及其他權利。
本會對該貢獻並不負有任何明示或默示的保密責任。
本會可能已有與該貢獻相似而正在考慮或發展中的想法或提案。
本會可以基於公益或為宣傳、推廣本服務之目的，以任何方式、在任何媒體上使用（或選擇不使用）該貢獻。
該貢獻將於本會不對您負任何責任的情形下，自動成為本會之財產。您於任何情形下皆無權利對本會主張任何形式的賠償或補償。
 
若您有違反前開事項之情事或之虞，本會有權於通知或未通知之情形下，隨時終止或限制您使用帳號（或其任何部分）或本服務之使用，並將本服務內任何相關「會員內容」加以移除並刪除。您同意若本服務之使用被終止或限制時，本會對您或任何第三人均不承擔責任。
不得為商業使用
您同意並承諾不對本服務任何部分或本服務（包括但不限於會員內容、網站內容、軟體及會員帳號等）之使用或存取，進行任何商業目的之使用。
您對本服務之授權
若您無合法權利得授權他人使用、修改、發行、散布、重製或公開展示某資料，並將前述權利轉授權第三人，請勿擅自將該資料上載、輸入或提供予本服務。您所上載、輸入或提供予本服務之任何資料，其權利仍為您或您的授權人所有；但任何資料一經您上載、輸入或提供予本服務時，即表示您已同意授權本會可以基於公益或為宣傳、推廣本服務之目的，進行使用、修改、發行、散布、重製或公開展示該等資料，並得在此範圍內將前述權利轉授權他人。您並保證本服務使用、修改、發行、散布、重製、公開展示或轉授權該資料，不致侵害任何第三人之相關權利，否則應對本會負損害賠償責任。
系統中斷或故障
本服務有時可能會出現中斷或故障等現象，或許將造成您使用上的不便、資料喪失、錯誤等情形。您於使用本服務時宜自行採取防護措施。本會對於您因使用（或無法使用）本服務而造成的損害，不負任何賠償責任。
與第三方網站之連結及第三方提供之內容
本服務可能會提供連結至其他機關單位(以下稱「第三方」)之網站。第三方之網站均由各該機關單位自行負責，不屬本服務控制及負責範圍之內。本會對任何檢索結果或外部連結，不擔保其有效性、正確性、合適性、及完整性。您也許會檢索或連結到一些不合適或不需要的網站，遇此情形時，本服務建議您不要瀏覽或儘速離開該等網站。您並同意本會無須為您連結至前述非屬於本服務之網站所生之任何損害，負損害賠償之責任。 本服務隨時會與其他機關單位等第三方（以下稱「內容提供者」）合作，由其提供包括政令、新聞、訊息、活動等不同內容供本服務刊登，且本服務於刊登時均將註明內容來源。基於尊重內容提供者之智慧財產權，本服務對其所提供之內容並不做實質之審查或修改。對該等內容之正確真偽，您宜自行判斷之，本會對該等內容之正確真偽不負任何責任。您若認為某些內容屬不適宜、侵權或有所不實，請逕向該內容提供者反應意見。
服務變更及通知
您同意本會保留於任何時間點，不經通知隨時修改、暫時或永久停止繼續提供本服務（或其任一部分）的權利。如依法或其他相關規定須為通知時，本服務得以包括但不限於：張貼於本服務網頁、電子郵件，或其他現在或未來合理之方式通知您，包括本服務條款之變更。但若您違反本服務條款，以未經授權的方式存取本服務，或於註冊時所提供之資料不正確或未更新，您將不會收到前述通知。當您經由授權的方式存取本服務，您即同意本服務所為之任何及所有給您的通知，均視為送達。
智慧財產權的保護
本服務所使用之程式、軟體及網站內容，包括但不限於：資訊、資料、圖片、檔案、網站架構、網頁設計及會員內容等，均由本會或其他權利人依法擁有其智慧財產權，包括但不限於專利權、著作權與專有技術等。任何人不得逕自修改、重製、散布、發行、公開發表、進行還原工程或反向組譯。若您欲引用或轉載前述資料，除明確為法律所允許者外，均須依法事前取得本會或其他權利人之書面同意。如有違反，您應負損害賠償責任。
智慧財產權或著作權之侵害處理
本會尊重他人之智慧財產權，同樣也要求本服務的使用者尊重他人之智慧財產權。本服務得對於可能屬侵權之使用者暫停或終止其帳戶。若您認為您的著作權或智慧財產權遭受侵害，請提供以下資訊予本服務：
您的正確資料與聯絡方式，並有異議情形時，同意將其資料提供給被檢舉人。
能合法代表著作權或智慧財產權利益之所有人之證明。
您所主張受侵害之著作或其他智慧財產權之描述，以及受侵害資料之描述。
您基於善意認為該利用未經著作權人其代理人或法律許可之聲明。
您在了解虛偽陳述之責任的前提下，對於上述載於您的通知上之資訊的正確性之聲明。    		    
		    </textarea>
		  </div> 
		  
      <div class="modal-footer">
		<label class="custom-control custom-checkbox">
		  <input type="checkbox" class="custom-control-input" name="acceptLaw" id="acceptLaw">
		  <span class="custom-control-indicator"></span>
		  <span class="custom-control-description">本人同意條約內容</span>
		</label>      
      	<button type="button" class="btn btn-primary" onclick="confirmAcceptLaw()">確定</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
      </div>		  
		         
      </div>
    </div>
  </div>
</div>
<!-- Modal ends Here, readme modal -->

<div class="container">   
<form class="form-signin" name="loginForm" id="loginForm" action="./login.do" method="post">

    <div><img src="./images/logo2.png" width="48" height="48" border="0"/>&nbsp;&nbsp;&nbsp;<b><font color="#041431">ZenLife - 禪生活保健</font></b></div>
   
    <br/>
   
   
    <c:if test="${ \"Y\" == loginCaptchaCodeEnable }">    
    <div class="form-group">
          <label for="captcha">驗證碼 <img src="./kaptcha.jpg?n=<%=System.currentTimeMillis()%>"/></label>
          <input class="form-control" type="text" id="captcha" name="captcha">
    </div>       
    </c:if>
    
    <div class="form-group">
          <label for="username">帳戶</label>
          <input class="form-control" type="text" id="username" name="username" maxlength="12">
    </div>   
    <div class="form-group">
          <label for="password">密碼</label>
          <input class="form-control" type="password" id="password" name="password" maxlength="25">
    </div>   
   	
   	
   	<c:if test="${\"\" != pageMessage && null != pageMessage}">
   	<p class="bg-warning"><c:out value="${pageMessage}" ></c:out></p>
   	</c:if>
   
    <button type="button" class="btn btn-lg btn-primary btn-block" name="btnSubmit" onclick="submitLoginForm()">登入</button>
    
    <button type="button" class="btn btn-lg btn-info btn-block" name="btnRegedit" onclick="showRegedit()">加入會員</button>
   
    <br/>
    
    <label>ZenLife 0.1 version</label>

</form>
</div>

</body>
</html>
