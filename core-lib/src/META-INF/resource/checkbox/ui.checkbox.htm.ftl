<#if nbspFirst == "Y" >&nbsp;</#if>
<label class="custom-control custom-checkbox">
	<input type="checkbox" class="${cssClass}" id="${id}" name="${name}" <#if checked == "Y" > checked="checked" </#if> <#if disabled == "Y" > disabled="disabled" </#if> <#if onchange?has_content > onchange="${onchange}" </#if> <#if onclick?has_content > onclick="${onclick}" </#if> >
	<span class="custom-control-indicator"></span>
	<span class="custom-control-description">${label}</span>
</label>