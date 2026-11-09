package com.memolive.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private WebView webView;
    private static final String HOME_URL = "https://sawti-chat-hv6l.whacka.app/";

    private static final String HIDE_BRANDING_JS =
        "(function() {" +
        " const hide = function() {" +
        "  const all = document.querySelectorAll('body *');" +
        "  all.forEach(function(el) {" +
        "   if (!el || !el.innerText) return;" +
        "   const t = el.innerText.trim();" +
        "   if (t.length > 100 || !(t.includes('Whacka') || t.includes('اصنع تطبيقك'))) return;" +
        "   let node = el;" +
        "   for (let i = 0; i < 6 && node && node !== document.body; i++, node = node.parentElement) {" +
        "    const s = window.getComputedStyle(node);" +
        "    const r = node.getBoundingClientRect();" +
        "    if ((s.position === 'fixed' || s.position === 'sticky') && r.bottom > window.innerHeight - 180) {" +
        "     node.style.setProperty('display', 'none', 'important');" +
        "     node.style.setProperty('visibility', 'hidden', 'important');" +
        "     break;" +
        "    }" +
        "   }" +
        "  });" +
        " };" +
        " hide();" +
        " new MutationObserver(hide).observe(document.documentElement, {childList:true,subtree:true,characterData:true});" +
        " setInterval(hide, 1500);" +
        "})();";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.evaluateJavascript(HIDE_BRANDING_JS, null);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        setContentView(webView);
        if (savedInstanceState != null) webView.restoreState(savedInstanceState);
        else webView.loadUrl(HOME_URL);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (webView != null) webView.saveState(outState);
    }
    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        if (webView != null) { webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
