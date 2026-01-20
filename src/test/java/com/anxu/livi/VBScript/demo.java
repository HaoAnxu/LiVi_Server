package com.anxu.livi.VBScript;

import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 【解析JSON，组装VBScript脚本】
 *
 * @Author: haoanxu
 * @Date: 2026/1/19
 */
public class demo {
    private static final String sapSession = """
            If Not IsObject(application) Then
               Set SapGuiAuto  = GetObject("SAPGUI")
               Set application = SapGuiAuto.GetScriptingEngine
            End If
            If Not IsObject(connection) Then
               Set connection = application.Children(0)
            End If
            If Not IsObject(session) Then
               Set session    = connection.Children(0)
            End If
            If IsObject(WScript) Then
               WScript.ConnectObject session,     "on"
               WScript.ConnectObject application, "on"
            End If
            session.findById("wnd[0]").maximize
            """;
    private static final String createScript_1 = """
            session.findById("wnd[0]/tbar[0]/okcd").text = "/nVA01"
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]/usr/ctxtVBAK-AUART").text = "{orderType}"
            session.findById("wnd[0]/usr/ctxtVBAK-VKORG").text = "{saleGroup}"
            session.findById("wnd[0]/usr/ctxtVBAK-VTWEG").text = "{saleChannel}"
            session.findById("wnd[0]/usr/ctxtVBAK-SPART").text = "{productGroup}"
            session.findById("wnd[0]/usr/ctxtVBAK-VKBUR").text = "{saleOffice}"
            session.findById("wnd[0]/usr/ctxtVBAK-VKGRP").text = "{saleClass}"
            session.findById("wnd[0]/usr/ctxtVBAK-VKGRP").caretPosition = 3
            session.findById("wnd[0]").sendVKey 0
            """;

    private static final String createScript_2 = """
            session.findById("wnd[0]/usr/subSUBSCREEN_HEADER:SAPMV45A:4021/txtVBKD-BSTKD").text = "{byOrderNo}"
            session.findById("wnd[0]/usr/subSUBSCREEN_HEADER:SAPMV45A:4021/ctxtVBKD-BSTDK").text = "{buyDate}"
            session.findById("wnd[0]/usr/subSUBSCREEN_HEADER:SAPMV45A:4021/subPART-SUB:SAPMV45A:4701/ctxtKUAGV-KUNNR").text = "{soldToParty}"
            session.findById("wnd[0]/usr/subSUBSCREEN_HEADER:SAPMV45A:4021/subPART-SUB:SAPMV45A:4701/ctxtKUWEV-KUNNR").text = "{shipToParty}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\01/ssubSUBSCREEN_BODY:SAPMV45A:4400/ssubHEADER_FRAME:SAPMV45A:4440/txtVBAK-KTEXT").text = "{project}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\01/ssubSUBSCREEN_BODY:SAPMV45A:4400/ssubHEADER_FRAME:SAPMV45A:4440/ctxtRV45A-KETDAT").text = "{shipDate}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\01/ssubSUBSCREEN_BODY:SAPMV45A:4400/ssubHEADER_FRAME:SAPMV45A:4440/ctxtRV45A-KETDAT").caretPosition = 10
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02").select
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]").sendVKey 0
            """;
    private static final String createScript_2_RWK = """
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtRV45A-KWMENG[2,{p}]").text = "{quantity}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").text = "{modelDes}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").text = "{modelClass}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").caretPosition = 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            On Error Resume Next\s
            session.findById("wnd[0]/tbar[1]/btn[18]").press
            On Error GoTo 0
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07").select
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").text = "Z0"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").caretPosition = 2
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 25
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{RWK1}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{RWK2}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{RWK3}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{RWK4}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{RWK5}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{RWK6}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{RWK7}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{RWK8}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{RWK9}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{RWK10}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{RWK11}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{RWK12}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{RWK13}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{RWK14}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{RWK15}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{RWK16}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{RWK17}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{RWK18}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{RWK19}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{RWK20}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{RWK21}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{RWK22}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{RWK23}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{RWK24}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{RWK25}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{RWK26}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{RWK27}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{RWK28}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{RWK29}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{RWK30}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{RWK31}"
            session.findById("wnd[0]/tbar[0]/btn[11]").press
            """;
    private static final String createScript_2_YVWE = """
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtRV45A-KWMENG[2,{p}]").text = "{quantity}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").text = "{modelDes}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").text = "{modelClass}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").caretPosition = 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[1]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            On Error Resume Next\s
            session.findById("wnd[0]/tbar[1]/btn[18]").press
            On Error GoTo 0
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07").select
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").text = "Z0"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").caretPosition = 2
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 25
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YVWE0}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YVWE1}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YVWE2}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YVWE3}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YVWE4}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YVWE5}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YVWE6}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YVWE7}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YVWE8}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YVWE9}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YVWE10}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YVWE11}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YVWE12}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YVWE13}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YVWE14}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YVWE15}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YVWE16}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YVWE17}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YVWE18}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YVWE19}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YVWE20}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YVWE21}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YVWE22}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YVWE23}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YVWE24}"
            session.findById("wnd[0]/tbar[0]/btn[11]").press
            """;
    private static final String createScript_2_YGWE = """
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtRV45A-KWMENG[2,{p}]").text = "{quantity}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").text = "{modelDes}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").text = "{modelClass}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").caretPosition = 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[1]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            On Error Resume Next\s
            session.findById("wnd[0]/tbar[1]/btn[18]").press
            On Error GoTo 0
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07").select
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").text = "Z0"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").caretPosition = 2
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 25
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YGWE0}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YGWE1}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YGWE2}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YGWE3}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YGWE4}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YGWE5}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YGWE6}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YGWE7}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YGWE8}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YGWE9}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YGWE10}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YGWE11}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YGWE12}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YGWE13}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YGWE14}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YGWE15}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YGWE16}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YGWE17}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YGWE18}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YGWE19}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YGWE20}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YGWE21}"
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/tbar[0]/btn[11]").press
            """;
    private static final String createScript_2_YE = """
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtRV45A-KWMENG[2,{p}]").text = "{quantity}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").text = "{modelDes}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").text = "{modelClass}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").caretPosition = 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            session.findById("wnd[1]/usr/tblSAPLCEI0VALUE_S/radRCTMS-SEL01[0,1]").selected = true
            session.findById("wnd[1]/tbar[0]/btn[8]").press
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            On Error Resume Next\s
            session.findById("wnd[0]/tbar[1]/btn[18]").press
            On Error GoTo 0
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 2
            session.findById("wnd[0]").sendVKey 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07").select
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").text = "Z0"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").caretPosition = 2
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 25
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YE1}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YE2}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YE3}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YE4}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YE5}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YE6}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YE7}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YE8}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YE9}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YE10}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YE11}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YE12}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YE13}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YE14}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YE15}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YE16}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YE17}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YE18}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YE19}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YE20}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YE21}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YE22}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YE23}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YE24}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YE25}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YE26}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YE27}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YE28}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YE29}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YE30}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YE31}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YE32}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YE33}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YE34}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YE35}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YE36}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YE37}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YE38}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YE39}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YE40}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YE41}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YE42}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YE43}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YE44}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YE45}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YE46}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YE47}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YE48}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YE49}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YE50}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YE51}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YE52}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YE53}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YE54}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YE55}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YE56}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YE57}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YE58}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YE59}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YE60}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YE61}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YE62}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YE63}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YE64}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YE65}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YE66}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YE67}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YE68}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YE69}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YE70}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YE71}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YE72}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YE73}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YE74}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YE75}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YE76}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YE77}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YE78}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YE79}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YE80}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YE81}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/tbar[0]/btn[11]").press
            """;
    private static final String createScript_2_YKH = """
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtRV45A-KWMENG[2,{p}]").text = "{quantity}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").text = "{modelDes}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").text = "{modelClass}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/txtVBAP-ARKTX[5,{p}]").caretPosition = 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/subSUBSCREEN_BUTTONS:SAPMV45A:4050/btnBT_POAN").press
            session.findById("wnd[1]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            On Error Resume Next\s
            session.findById("wnd[0]/tbar[1]/btn[18]").press
            On Error GoTo 0
            
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 2
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07").select
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").text = "Z0"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").setFocus
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\07/ssubSUBSCREEN_BODY:SAPMV45A:4500/tblSAPMV45ATCTRL_PEIN/ctxtVBEP-ETTYP[8,0]").caretPosition = 2
            session.findById("wnd[0]").sendVKey 0
            session.findById("wnd[0]/tbar[0]/btn[3]").press
            
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_OVERVIEW/tabpT\\02/ssubSUBSCREEN_BODY:SAPMV45A:4401/subSUBSCREEN_TC:SAPMV45A:4900/tblSAPMV45ATCTRL_U_ERF_AUFTRAG/ctxtRV45A-MABNR[1,{p}]").caretPosition = 4
            session.findById("wnd[0]").sendVKey 25
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH1}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH2}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH3}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH4}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH5}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH6}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH7}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH8}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH9}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH10}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YKH11}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH12}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH13}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH14}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH15}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH16}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH17}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH18}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH19}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH20}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH21}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH22}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YKH23}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH24}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH25}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH26}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH27}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH28}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH29}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH30}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH31}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH32}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH33}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH34}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YKH35}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH36}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH37}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH38}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH39}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH40}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH41}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH42}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH43}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH44}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH45}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH46}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YKH47}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH48}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH49}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH50}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH51}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH52}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH53}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH54}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH55}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH56}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH57}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH58}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YKH59}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH60}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH61}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH62}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH63}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH64}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH65}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH66}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH67}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH68}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH69}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH70}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YKH71}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH72}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH73}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH74}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH75}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH76}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH77}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH78}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH79}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH80}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH81}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH82}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH83}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH84}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH85}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH86}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH87}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH88}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH89}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH90}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH91}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH92}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH93}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,10]").text = "{YKH94}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").text = "{YKH95}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").setFocus
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,11]").caretPosition = 1
            session.findById("wnd[0]/usr/btnNACH").press
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,0]").text = "{YKH96}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,1]").text = "{YKH97}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,2]").text = "{YKH98}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,3]").text = "{YKH99}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,4]").text = "{YKH100}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,5]").text = "{YKH101}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,6]").text = "{YKH102}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,7]").text = "{YKH103}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,8]").text = "{YKH104}"
            session.findById("wnd[0]/usr/subCHARACTERISTICS:SAPLCEI0:1400/tblSAPLCEI0CHARACTER_VALUES/ctxtRCTMS-MWERT[1,9]").text = "{YKH105}"
            session.findById("wnd[0]/tbar[0]/btn[11]").press
            """;
    private static final String createScript_4_1 = """
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").currentCellColumn = "FELD"
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").doubleClickCurrentCell
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_HEAD/tabpT\\13/ssubSUBSCREEN_BODY:SAPMV45A:4309/cmbVBAK-KVGR1").key = "128"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_HEAD/tabpT\\13/ssubSUBSCREEN_BODY:SAPMV45A:4309/cmbVBAK-KVGR2").key = "103"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_HEAD/tabpT\\13/ssubSUBSCREEN_BODY:SAPMV45A:4309/cmbVBAK-KVGR4").key = "206"
            session.findById("wnd[0]/tbar[1]/btn[5]").press
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").currentCellColumn = "FELD"
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").doubleClickCurrentCell
            session.findById("wnd[0]").sendVKey 4
            session.findById("wnd[1]").sendVKey 2
            session.findById("wnd[0]/tbar[1]/btn[5]").press
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").currentCellColumn = "FELD"
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").doubleClickCurrentCell
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_HEAD/tabpT\\09/ssubSUBSCREEN_BODY:SAPMV45A:4352/subSUBSCREEN_PARTNER_OVERVIEW:SAPLV09C:1000/tblSAPLV09CGV_TC_PARTNER_OVERVIEW/ctxtGVS_TC_DATA-REC-PARTNER[1,4]").text = "{salesStaff}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_HEAD/tabpT\\09/ssubSUBSCREEN_BODY:SAPMV45A:4352/subSUBSCREEN_PARTNER_OVERVIEW:SAPLV09C:1000/tblSAPLV09CGV_TC_PARTNER_OVERVIEW/ctxtGVS_TC_DATA-REC-PARTNER[1,4]").caretPosition = 5
            session.findById("wnd[0]/tbar[1]/btn[5]").press
            """;
    private static final String createScript_4_2_Common = """
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").currentCellColumn = "FELD"
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").doubleClickCurrentCell
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,7]").text = "{ptpUnitPriceTaxIn}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,7]").caretPosition = 15
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 10
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 20
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,2]").text = "{ptpUnitPriceTaxIn}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,9]").text = "{ptpUnitPriceTaxEx}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,9]").caretPosition = 16
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 10
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 0
            session.findById("wnd[0]/tbar[1]/btn[5]").press
            """;
    private static final String createScript_4_2_RWK = """
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").currentCellColumn = "FELD"
            session.findById("wnd[0]/usr/cntlGRID1/shellcont/shell/shellcont[1]/shell").doubleClickCurrentCell
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,7]").text = "{ptpUnitPriceTaxIn}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,7]").caretPosition = 13
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 10
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 20
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,2]").text = "{ptpUnitPriceTaxIn}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,2]").caretPosition = 16
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/subSUBSCREEN_PUSHBUTTONS:SAPLV69A:1000/btnBT_KOAN").press
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/ctxtKOMV-KSCHL[1,1]").text = "ZFTP"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,1]").text = "{ptpUnitPriceTaxEx}"
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN/txtKOMV-KBETR[3,1]").caretPosition = 13
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 18
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 8
            session.findById("wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\05/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN").verticalScrollbar.position = 0
            session.findById("wnd[0]/tbar[1]/btn[5]").press
            """;

    public static void main(String[] args) {
        String jsonStr = "{\"orderType\":\"ZST\",\"factory\":\"WXAC\",\"reason\":\"\",\"soldToParty\":\"IC-CN20\",\"buyDate\":\"15.01.2026\",\"project\":\"全国政协1号楼维修改造项目二期\",\"shipDate\":\"31.01.2026\",\"oobId\":244,\"isSave\":true,\"saleGroup\":\"CN04\",\"salesStaff\":\"89458\",\"createBy\":\"173\",\"saleChannel\":10,\"productGroup\":20,\"orderDes\":\"全国政协1号楼维修改造项目二期\",\"comCode\":\"WXAC\",\"saleClass\":\"\",\"deptCode\":\"IR-FPPM\",\"shipToParty\":\"IC-CN20\",\"byOrderNo\":\"FBJG2514344FEORNCPF-4510887527\",\"productList\":[{\"modelDes\":\"YVWE420CA50B22WAX\",\"omsiId\":874,\"quantity\":1,\"isSyn\":true,\"productId\":615,\"modelClass\":\"YVWE\",\"firstDate\":\"2026-01-31\",\"ptpUnitPriceTaxIn\":\"418783.65\",\"modelSelect\":[{\"selectList\":[{\"cn_name\":\"机型\",\"fof_value\":\"YVWE\",\"value_des\":\"YVWE\",\"rpa_value\":\"YVWE\",\"rpa_key\":\"V_YVWEMODEL\"}],\"propertyName\":\"V_YVWEMODEL\",\"sap_value\":\"YVWE\",\"tip\":\"\",\"isDisabled\":true,\"labelName\":\"机型\",\"tipFlag\":\"false\",\"sap_des\":\"YVWE\",\"value\":\"YVWE\",\"_X_ROW_KEY\":\"row_1319\"},{\"selectList\":[{\"cn_name\":\"名义冷量\",\"fof_value\":\"100\",\"value_des\":\"100\",\"rpa_value\":\"100\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"130\",\"value_des\":\"130\",\"rpa_value\":\"130\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"170\",\"value_des\":\"170\",\"rpa_value\":\"170\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"200\",\"value_des\":\"200\",\"rpa_value\":\"200\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"240\",\"value_des\":\"240\",\"rpa_value\":\"240\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"270\",\"value_des\":\"270\",\"rpa_value\":\"270\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"280\",\"value_des\":\"280\",\"rpa_value\":\"280\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"310\",\"value_des\":\"310\",\"rpa_value\":\"310\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"355\",\"value_des\":\"355\",\"rpa_value\":\"355\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"360\",\"value_des\":\"360\",\"rpa_value\":\"360\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"420\",\"value_des\":\"420\",\"rpa_value\":\"420\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"430\",\"value_des\":\"430\",\"rpa_value\":\"430\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"460\",\"value_des\":\"460\",\"rpa_value\":\"460\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"500\",\"value_des\":\"500\",\"rpa_value\":\"500\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"550\",\"value_des\":\"550\",\"rpa_value\":\"550\",\"rpa_key\":\"V_YVWENOMCAP\"}],\"propertyName\":\"V_YVWENOMCAP\",\"sap_value\":\"420\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"名义冷量\",\"tipFlag\":\"false\",\"sap_des\":\"420\",\"value\":\"420\",\"_X_ROW_KEY\":\"row_1320\"},{\"selectList\":[{\"cn_name\":\"运用\",\"fof_value\":\"Air-conditioning\",\"value_des\":\"空调\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWEAPPL\"},{\"cn_name\":\"运用\",\"fof_value\":\"ITS\",\"value_des\":\"蓄冰双工况\",\"rpa_value\":\"D\",\"rpa_key\":\"V_YVWEAPPL\"}],\"propertyName\":\"V_YVWEAPPL\",\"sap_value\":\"C\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"运用\",\"tipFlag\":\"false\",\"sap_des\":\"空调\",\"value\":\"Air-conditioning\",\"_X_ROW_KEY\":\"row_1321\"},{\"selectList\":[{\"cn_name\":\"制冷剂\",\"fof_value\":\"A\",\"value_des\":\"HFC-134a\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEREF\"}],\"propertyName\":\"V_YVWEREF\",\"sap_value\":\"A\",\"tip\":\"\",\"isDisabled\":true,\"labelName\":\"制冷剂\",\"tipFlag\":\"false\",\"sap_des\":\"HFC-134a\",\"value\":\"R-134a\",\"_X_ROW_KEY\":\"row_1322\"},{\"selectList\":[{\"cn_name\":\"电压\",\"fof_value\":\"380V/50Hz/3Ph\",\"value_des\":\"380/3/50\",\"rpa_value\":\"50\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"400V/50Hz/3Ph\",\"value_des\":\"400/3/50\",\"rpa_value\":\"53\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"415V/50Hz/3Ph\",\"value_des\":\"415/3/50\",\"rpa_value\":\"55\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"380V/60Hz/3Ph\",\"value_des\":\"380/3/60\",\"rpa_value\":\"60\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"400V/60Hz/3Ph\",\"value_des\":\"400/3/60\",\"rpa_value\":\"63\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"415V/60Hz/3Ph\",\"value_des\":\"415/3/60\",\"rpa_value\":\"65\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"460V/60Hz/3Ph\",\"value_des\":\"66\",\"rpa_value\":\"66\",\"rpa_key\":\"V_YVWEVOLTS\"}],\"propertyName\":\"V_YVWEVOLTS\",\"sap_value\":\"50\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"电压\",\"tipFlag\":\"false\",\"sap_des\":\"380/3/50\",\"value\":\"380V/50Hz/3Ph\",\"_X_ROW_KEY\":\"row_1323\"},{\"selectList\":[{\"cn_name\":\"设计系列号\",\"fof_value\":\"A\",\"value_des\":\"设计系列A\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEDESIGNLVL\"},{\"cn_name\":\"设计系列号\",\"fof_value\":\"B\",\"value_des\":\"设计系列B\",\"rpa_value\":\"B\",\"rpa_key\":\"V_YVWEDESIGNLVL\"}],\"propertyName\":\"V_YVWEDESIGNLVL\",\"sap_value\":\"A\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"设计系列号\",\"tipFlag\":\"false\",\"sap_des\":\"设计系列A\",\"value\":\"A\",\"_X_ROW_KEY\":\"row_1324\"},{\"selectList\":[{\"cn_name\":\"压缩机种类\",\"fof_value\":\"N\",\"value_des\":\"定VI空调压缩机\",\"rpa_value\":\"W\",\"rpa_key\":\"V_YVWECPRVAR\"},{\"cn_name\":\"压缩机种类\",\"fof_value\":\"Y\",\"value_des\":\"可变VI空调压缩机\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWECPRVAR\"}],\"propertyName\":\"V_YVWECPRVAR\",\"sap_value\":\"W\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"压缩机种类\",\"tipFlag\":\"false\",\"sap_des\":\"定VI空调压缩机\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_1325\"},{\"selectList\":[{\"cn_name\":\"容器规范\",\"fof_value\":\"China PV Code\",\"value_des\":\"GB压力容器规范\",\"rpa_value\":\"G\",\"rpa_key\":\"V_YVWEVESSEL\"},{\"cn_name\":\"容器规范\",\"fof_value\":\"ASME Code\",\"value_des\":\"ASME压力容器规范\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEVESSEL\"},{\"cn_name\":\"容器规范\",\"fof_value\":\"目前FOF中无对应字段，需结合SQ\",\"value_des\":\"DOSH压力容器规范\",\"rpa_value\":\"D\",\"rpa_key\":\"V_YVWEVESSEL\"}],\"propertyName\":\"V_YVWEVESSEL\",\"sap_value\":\"G\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"容器规范\",\"tipFlag\":\"false\",\"sap_des\":\"GB压力容器规范\",\"value\":\"China PV Code\",\"_X_ROW_KEY\":\"row_1326\"},{\"selectList\":[{\"cn_name\":\"蒸冷水侧连接形式\",\"fof_value\":\"Welded Flange (HG/T 20615)\",\"value_des\":\"HG焊接法兰\",\"rpa_value\":\"H\",\"rpa_key\":\"V_YVWEWBCON\"},{\"cn_name\":\"蒸冷水侧连接形式\",\"fof_value\":\"Victaulic Groove\",\"value_des\":\"卡箍\",\"rpa_value\":\"G\",\"rpa_key\":\"V_YVWEWBCON\"}],\"propertyName\":\"V_YVWEWBCON\",\"sap_value\":\"H\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸冷水侧连接形式\",\"tipFlag\":\"false\",\"sap_des\":\"HG焊接法兰\",\"value\":\"Welded Flange (HG/T 20615)\",\"_X_ROW_KEY\":\"row_1327\"},{\"selectList\":[{\"cn_name\":\"蒸发器流程\",\"fof_value\":\"1\",\"value_des\":\"1 流程\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWEEVAPPASS\"},{\"cn_name\":\"蒸发器流程\",\"fof_value\":\"2\",\"value_des\":\"2 流程\",\"rpa_value\":\"2\",\"rpa_key\":\"V_YVWEEVAPPASS\"},{\"cn_name\":\"蒸发器流程\",\"fof_value\":\"3\",\"value_des\":\"3 流程\",\"rpa_value\":\"3\",\"rpa_key\":\"V_YVWEEVAPPASS\"}],\"propertyName\":\"V_YVWEEVAPPASS\",\"sap_value\":\"2\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸发器流程\",\"tipFlag\":\"false\",\"sap_des\":\"2 流程\",\"value\":\"2\",\"_X_ROW_KEY\":\"row_1328\"},{\"selectList\":[{\"cn_name\":\"蒸发器水侧设计压力\",\"fof_value\":\"150 PSIG DWP\",\"value_des\":\"水侧150磅\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWEEVAPDWP\"},{\"cn_name\":\"蒸发器水侧设计压力\",\"fof_value\":\"300 PSIG DWP\",\"value_des\":\"水侧300磅\",\"rpa_value\":\"3\",\"rpa_key\":\"V_YVWEEVAPDWP\"}],\"propertyName\":\"V_YVWEEVAPDWP\",\"sap_value\":\"3\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸发器水侧设计压力\",\"tipFlag\":\"false\",\"sap_des\":\"水侧300磅\",\"value\":\"300 PSIG DWP\",\"_X_ROW_KEY\":\"row_1329\"},{\"selectList\":[{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"AH\",\"value_des\":\"AH\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"HA\",\"value_des\":\"HA\",\"rpa_value\":\"H\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"CB\",\"value_des\":\"CB\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"KJ\",\"value_des\":\"KJ\",\"rpa_value\":\"K\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"GN\",\"value_des\":\"GN\",\"rpa_value\":\"G\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"PF\",\"value_des\":\"PF\",\"rpa_value\":\"P\",\"rpa_key\":\"V_YVWEEVAPARR\"}],\"propertyName\":\"V_YVWEEVAPARR\",\"sap_value\":\"C\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸发器水箱布置\",\"tipFlag\":\"false\",\"sap_des\":\"CB\",\"value\":\"CB\",\"_X_ROW_KEY\":\"row_1330\"},{\"selectList\":[{\"cn_name\":\"冷凝器流程\",\"fof_value\":\"1\",\"value_des\":\"1 流程\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWECONDPASS\"},{\"cn_name\":\"冷凝器流程\",\"fof_value\":\"2\",\"value_des\":\"2 流程\",\"rpa_value\":\"2\",\"rpa_key\":\"V_YVWECONDPASS\"}],\"propertyName\":\"V_YVWECONDPASS\",\"sap_value\":\"2\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器流程\",\"tipFlag\":\"false\",\"sap_des\":\"2 流程\",\"value\":\"2\",\"_X_ROW_KEY\":\"row_1331\"},{\"selectList\":[{\"cn_name\":\"冷凝器水箱设计压力\",\"fof_value\":\"150 PSIG DWP\",\"value_des\":\"水侧150磅\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWECONDDWP\"},{\"cn_name\":\"冷凝器水箱设计压力\",\"fof_value\":\"300 PSIG DWP\",\"value_des\":\"水侧300磅\",\"rpa_value\":\"3\",\"rpa_key\":\"V_YVWECONDDWP\"}],\"propertyName\":\"V_YVWECONDDWP\",\"sap_value\":\"3\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器水箱设计压力\",\"tipFlag\":\"false\",\"sap_des\":\"水侧300磅\",\"value\":\"300 PSIG DWP\",\"_X_ROW_KEY\":\"row_1332\"},{\"selectList\":[{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"PQ\",\"value_des\":\"PQ\",\"rpa_value\":\"P\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"QP\",\"value_des\":\"QP\",\"rpa_value\":\"Q\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"RS\",\"value_des\":\"RS\",\"rpa_value\":\"R\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"TU\",\"value_des\":\"TU\",\"rpa_value\":\"T\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"VW\",\"value_des\":\"VW\",\"rpa_value\":\"V\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"XY\",\"value_des\":\"XY\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWECONDARR\"}],\"propertyName\":\"V_YVWECONDARR\",\"sap_value\":\"R\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器水箱布置\",\"tipFlag\":\"false\",\"sap_des\":\"RS\",\"value\":\"RS\",\"_X_ROW_KEY\":\"row_1333\"},{\"selectList\":[{\"cn_name\":\"冷凝器水箱形式\",\"fof_value\":\"N\",\"value_des\":\"紧凑式水箱\",\"rpa_value\":\"CA\",\"rpa_key\":\"V_YVWECWBTYPE\"},{\"cn_name\":\"冷凝器水箱形式\",\"fof_value\":\"150 PSIG DWP\",\"value_des\":\"小球清洗水箱\",\"rpa_value\":\"BA(150 PSIG DWP)\",\"rpa_key\":\"V_YVWECWBTYPE\"},{\"cn_name\":\"冷凝器水箱形式\",\"fof_value\":\"300 PSIG DWP\",\"value_des\":\"小球清洗水箱\",\"rpa_value\":\"BA(300 PSIG DWP)\",\"rpa_key\":\"V_YVWECWBTYPE\"}],\"propertyName\":\"V_YVWECWBTYPE\",\"sap_value\":\"CA\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器水箱形式\",\"tipFlag\":\"false\",\"sap_des\":\"紧凑式水箱\",\"value\":\"紧凑式水箱\",\"_X_ROW_KEY\":\"row_1334\"},{\"selectList\":[{\"cn_name\":\"截止阀选项\",\"fof_value\":\"N\",\"value_des\":\"不带隔离阀\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEVALVE\"},{\"cn_name\":\"截止阀选项\",\"fof_value\":\"Y\",\"value_des\":\"冷凝器隔离阀\",\"rpa_value\":\"B\",\"rpa_key\":\"V_YVWEVALVE\"}],\"propertyName\":\"V_YVWEVALVE\",\"sap_value\":\"X\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"截止阀选项\",\"tipFlag\":\"false\",\"sap_des\":\"不带隔离阀\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_1335\"},{\"selectList\":[{\"cn_name\":\"保温\",\"fof_value\":\"3/4\\\" (19 mm)\",\"value_des\":\"蒸发器19mm保温\",\"rpa_value\":\"S\",\"rpa_key\":\"V_YVWEINS\"},{\"cn_name\":\"保温\",\"fof_value\":\"1 1/2\\\" (38 mm)\",\"value_des\":\"蒸发器38mm保温\",\"rpa_value\":\"D\",\"rpa_key\":\"V_YVWEINS\"},{\"cn_name\":\"保温\",\"fof_value\":\"1\\\" (25mm)\",\"value_des\":\"蒸发器25mm保温\",\"rpa_value\":\"F\",\"rpa_key\":\"V_YVWEINS\"},{\"cn_name\":\"保温\",\"fof_value\":\"Not Required\",\"value_des\":\"没有保温\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEINS\"}],\"propertyName\":\"V_YVWEINS\",\"sap_value\":\"S\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"保温\",\"tipFlag\":\"false\",\"sap_des\":\"蒸发器19mm保温\",\"value\":\"3/4\\\" (19 mm)\",\"_X_ROW_KEY\":\"row_1336\"},{\"selectList\":[{\"cn_name\":\"隔音组件\",\"fof_value\":\"N\",\"value_des\":\"不带隔音罩（无隔音套件）\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWENOISERDCT\"},{\"cn_name\":\"隔音组件\",\"fof_value\":\"Y\",\"value_des\":\"部分区域包隔音罩(隔音套件1)\",\"rpa_value\":\"B\",\"rpa_key\":\"V_YVWENOISERDCT\"},{\"cn_name\":\"隔音组件\",\"fof_value\":\"？？？\",\"value_des\":\"整机包隔音罩(隔音套件2)\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWENOISERDCT\"}],\"propertyName\":\"V_YVWENOISERDCT\",\"sap_value\":\"X\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"隔音组件\",\"tipFlag\":\"false\",\"sap_des\":\"不带隔音罩（无隔音套件）\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_1337\"},{\"propertyName\":\"V_YVWEDEST\",\"sap_value\":\"C\",\"tip\":\"\",\"isDisabled\":true,\"labelName\":\"发运目的地\",\"tipFlag\":\"false\",\"sap_des\":\"中国大陆\",\"value\":\"中国大陆\",\"_X_ROW_KEY\":\"row_1338\"},{\"selectList\":[{\"cn_name\":\"发运方式\",\"fof_value\":\"Form1: Refrigerant shipped in unit\",\"value_des\":\"发运方式1\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWEFORM\"},{\"cn_name\":\"发运方式\",\"fof_value\":\"Form2: Refrigerant not included\",\"value_des\":\"发运方式2\",\"rpa_value\":\"2\",\"rpa_key\":\"V_YVWEFORM\"}],\"propertyName\":\"V_YVWEFORM\",\"sap_value\":\"1\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"发运方式\",\"tipFlag\":\"false\",\"sap_des\":\"发运方式1\",\"value\":\"Form1: Refrigerant shipped in unit\",\"_X_ROW_KEY\":\"row_1339\"},{\"selectList\":[{\"cn_name\":\"滤波器\",\"fof_value\":\"N\",\"value_des\":\"不带滤波器\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEFILTER\"},{\"cn_name\":\"滤波器\",\"fof_value\":\"Y\",\"value_des\":\"带滤波器\",\"rpa_value\":\"F\",\"rpa_key\":\"V_YVWEFILTER\"}],\"propertyName\":\"V_YVWEFILTER\",\"sap_value\":\"X\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"滤波器\",\"tipFlag\":\"false\",\"sap_des\":\"不带滤波器\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_1340\"},{\"selectList\":[{\"cn_name\":\"楼宇自动化系统\",\"fof_value\":\"Modbus\",\"value_des\":\"Modbus\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEBAS\"},{\"cn_name\":\"楼宇自动化系统\",\"fof_value\":\"SC-Equipment – BACnet/N2\",\"value_des\":\"SC-Equipment – BACnet/N2\",\"rpa_value\":\"S(SC-Equipment – BACnet/N2)\",\"rpa_key\":\"V_YVWEBAS\"},{\"cn_name\":\"楼宇自动化系统\",\"fof_value\":\"LON E-link\",\"value_des\":\"LON E-link\",\"rpa_value\":\"L\",\"rpa_key\":\"V_YVWEBAS\"},{\"cn_name\":\"楼宇自动化系统\",\"fof_value\":\"SC-Equip Board\",\"value_des\":\"SC-Equip Board\",\"rpa_value\":\"S(SC-Equip Board)\",\"rpa_key\":\"V_YVWEBAS\"}],\"propertyName\":\"V_YVWEBAS\",\"sap_value\":\"S\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"楼宇自动化系统\",\"tipFlag\":\"false\",\"sap_des\":\"SC-Equipment – BACnet/N2\",\"value\":\"SC-Equipment – BACnet/N2\",\"_X_ROW_KEY\":\"row_1341\"},{\"selectList\":[{\"cn_name\":\"TMR智能设备支持套件\",\"value_des\":\"不需要TMR智能设备支持套件\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWETMR\"},{\"cn_name\":\"TMR智能设备支持套件\",\"value_des\":\"需要TMR智能设备支持套件\",\"rpa_value\":\"Y\",\"rpa_key\":\"V_YVWETMR\"}],\"propertyName\":\"V_YVWETMR\",\"sap_value\":\"\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"TMR智能设备支持套件\",\"tipFlag\":\"false\",\"sap_des\":\"\",\"value\":\"\",\"_X_ROW_KEY\":\"row_1342\"},{\"selectList\":[{\"cn_name\":\"智能设备支持套件\",\"value_des\":\"不需要智能设备支持套件(中文)\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWESCAP\"},{\"cn_name\":\"智能设备支持套件\",\"value_des\":\"需要智能设备支持套件(中文)\",\"rpa_value\":\"Y\",\"rpa_key\":\"V_YVWESCAP\"}],\"propertyName\":\"V_YVWESCAP\",\"sap_value\":\"\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"智能设备支持套件\",\"tipFlag\":\"false\",\"sap_des\":\"\",\"value\":\"\",\"_X_ROW_KEY\":\"row_1343\"},{\"selectList\":[{\"cn_name\":\"目的地国家\",\"value_des\":\"中国\",\"rpa_value\":\"CN\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"澳大利亚\",\"rpa_value\":\"AT\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"新西兰\",\"rpa_value\":\"NZ\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"新加坡\",\"rpa_value\":\"SG\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"香港\",\"rpa_value\":\"HK\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"印度\",\"rpa_value\":\"IN\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"泰国\",\"rpa_value\":\"TH\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"印度尼西亚\",\"rpa_value\":\"ID\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"沙特阿拉伯\",\"rpa_value\":\"SA\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"英国\",\"rpa_value\":\"GB\",\"rpa_key\":\"V_YVWEDSTN\"},{\"cn_name\":\"目的地国家\",\"value_des\":\"台湾\",\"rpa_value\":\"TW\",\"rpa_key\":\"V_YVWEDSTN\"}],\"propertyName\":\"V_YVWEDSTN\",\"sap_value\":\"CN\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"目的地国家\",\"tipFlag\":\"false\",\"sap_des\":\"中国\",\"_X_ROW_KEY\":\"row_1344\"}],\"ptpUnitPriceTaxEx\":\"370605.33\",\"_X_ROW_KEY\":\"row_1309\",\"btp\":\"418783.65\"}],\"saleOffice\":\"CN41\"}";
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        StrBuilder vbScript = new StrBuilder();
        vbScript.append(sapSession);

        // 产品组
        String productGroup;
        if (jsonObject.getString("deptCode").equals("CSD")) {
            productGroup = "10";
        } else if (jsonObject.getString("deptCode").equals("IR-FPPM")) {
            productGroup = "20";
        } else {
            throw new RuntimeException("部门不支持");
        }

        // 销售组
        String saleClass;
        if (!jsonObject.getString("saleClass").isEmpty()) {
            saleClass = jsonObject.getString("saleClass");
        } else {
            switch (jsonObject.getString("saleOffice")) {
                case "CNC6":
                    saleClass = "BN5";
                    break;
                case "CND1":
                    saleClass = "BQ0";
                    break;
                default:
                    saleClass = "041";
            }
        }

        //Script_1
        Map<String, String> script1Map = new HashMap<>();
        script1Map.put("orderType", jsonObject.getString("orderType"));
        script1Map.put("saleGroup", jsonObject.getString("saleGroup"));
        script1Map.put("saleChannel", jsonObject.getString("saleChannel"));
        script1Map.put("productGroup", productGroup);
        script1Map.put("saleOffice", jsonObject.getString("saleOffice"));
        script1Map.put("saleClass", saleClass);

        String scriptPart1 = PlaceholderReplacer.replace(createScript_1, script1Map);
        vbScript.append(scriptPart1);

        //Script_2
        String buyDate = jsonObject.getString("buyDate").replace(".", "/");
        String shipDate = jsonObject.getString("shipDate").replace(".", "/");
        Map<String, String> script2Map = new HashMap<>();
        script2Map.put("byOrderNo", jsonObject.getString("byOrderNo"));
        script2Map.put("soldToParty", jsonObject.getString("soldToParty"));
        script2Map.put("shipToParty", jsonObject.getString("shipToParty"));
        script2Map.put("project", jsonObject.getString("project"));
        script2Map.put("buyDate", buyDate);
        script2Map.put("shipDate", shipDate);

        String scriptPart2_1 = PlaceholderReplacer.replace(createScript_2, script2Map);
        vbScript.append(scriptPart2_1);

        JSONArray productList = jsonObject.getJSONArray("productList");
        boolean isFirstProduct = true;
        for (int i = 0; i < productList.size(); i++) {
            String position = isFirstProduct ? "0" : "1";
            isFirstProduct = false;
            JSONObject currentProduct = (JSONObject) productList.get(i);
            Map<String, String> script2_2Map = new HashMap<>();
            script2_2Map.put("quantity", currentProduct.getString("quantity"));
            script2_2Map.put("modelDes", currentProduct.getString("modelDes"));
            if(currentProduct.getString("modelClass").equals("YVWT")){
                script2_2Map.put("modelClass", "YVWE");
            }else {
                script2_2Map.put("modelClass", currentProduct.getString("modelClass"));
            }
            script2_2Map.put("p", position);

            JSONArray characterList = currentProduct.getJSONArray("modelSelect");
            int index = 0;
            String scriptPart2_2;
            switch (currentProduct.getString("modelClass")) {
                case "RWK":
                    for (Object currentItem : characterList) {
                        JSONObject currentCharacter = (JSONObject) currentItem;
                        script2_2Map.put("RWK"+index, currentCharacter.getString("sap_value"));
                        index++;
                    }
                    scriptPart2_2 = PlaceholderReplacer.replace(createScript_2_RWK, script2_2Map);
                    vbScript.append(scriptPart2_2);
                    break;
                case "YVWE", "YVWT":
                    for (int j = 0; j < characterList.size(); j++) {
                        if (j == 24) continue;
                        JSONObject currentCharacter = (JSONObject) characterList.get(j);
                        script2_2Map.put("YVWE"+index, currentCharacter.getString("sap_value"));
                        index++;
                    }
                    scriptPart2_2 = PlaceholderReplacer.replace(createScript_2_YVWE, script2_2Map);
                    vbScript.append(scriptPart2_2);
                    break;
                case "YGWE":
                    for (int j = 0; j < characterList.size(); j++) {
                        if (j == 24) continue;
                        JSONObject currentCharacter = (JSONObject) characterList.get(j);
                        script2_2Map.put("YGWE"+index, currentCharacter.getString("sap_value"));
                        index++;
                    }
                    scriptPart2_2 = PlaceholderReplacer.replace(createScript_2_YGWE, script2_2Map);
                    vbScript.append(scriptPart2_2);
                    break;
                case "YE":
                    break;
                case "YKH":
                    break;
                default:
                    throw new RuntimeException("产品类型不支持");
            }
            //如果当前是最后一行
            if (i == productList.size() - 1) {
                vbScript.append("session.findById(\"wnd[1]/usr/btnSPOP-VAROPTION2\").press");
            } else {
                vbScript.append("session.findById(\"wnd[1]/usr/btnCANCEL\").press\n" +
                        "session.findById(\"wnd[0]/tbar[0]/btn[3]\").press");
            }
        }

        //Script_4
        Map<String,String> script4Map = new HashMap<>();
        script4Map.put("salesStaff", jsonObject.getString("salesStaff"));
        String scriptPart4 = PlaceholderReplacer.replace(createScript_4_1, script4Map);
        vbScript.append(scriptPart4);
        for (Object cur : productList) {
            JSONObject currentProduct = (JSONObject) cur;
            Map<String,String> script4_2Map = new HashMap<>();
            script4_2Map.put("ptpUnitPriceTaxIn", currentProduct.getString("ptpUnitPriceTaxIn"));
            script4_2Map.put("ptpUnitPriceTaxEx", currentProduct.getString("ptpUnitPriceTaxEx"));
            if(currentProduct.getString("modelClass").equals("RWK")){
                String scriptPart4_2 = PlaceholderReplacer.replace(createScript_4_2_RWK, script4_2Map);
                vbScript.append(scriptPart4_2);
            }else {
                String scriptPart4_2 = PlaceholderReplacer.replace(createScript_4_2_Common, script4_2Map);
                vbScript.append(scriptPart4_2);
            }
        }
        vbScript.append("session.findById(\"wnd[0]/tbar[0]/btn[11]\").press");
        System.out.println(vbScript);
    }
}
