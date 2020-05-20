.class Lapp/para/webview/MainActivity$1;
.super Landroid/webkit/WebViewClient;
.source "MainActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lapp/para/webview/MainActivity;->InitWebView()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lapp/para/webview/MainActivity;


# direct methods
.method constructor <init>(Lapp/para/webview/MainActivity;)V
    .locals 0
    .param p1, "this$0"    # Lapp/para/webview/MainActivity;

    .prologue
    .line 45
    iput-object p1, p0, Lapp/para/webview/MainActivity$1;->this$0:Lapp/para/webview/MainActivity;

    invoke-direct {p0}, Landroid/webkit/WebViewClient;-><init>()V

    return-void
.end method


# virtual methods
.method public shouldOverrideUrlLoading(Landroid/webkit/WebView;Ljava/lang/String;)Z
    .locals 1
    .param p1, "view"    # Landroid/webkit/WebView;
    .param p2, "url"    # Ljava/lang/String;

    .prologue
    .line 48
    invoke-virtual {p1, p2}, Landroid/webkit/WebView;->loadUrl(Ljava/lang/String;)V

    .line 49
    const/4 v0, 0x1

    return v0
.end method
