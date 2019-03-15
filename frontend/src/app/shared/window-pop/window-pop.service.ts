export class WindowPopService {
    private active: Boolean = false;
    private title: string;
    private context: string;
    private details: string;
    private logout: Boolean = false;
    private deleteImage: Boolean = false;
    private cookies: Boolean = false;
    private cookiesToDB: Boolean = false;

    activate() {
        this.active = true;
    }

    deactivate() {
        this.active = false;
        this.logout = false;
        this.deleteImage = false;
        this.cookies = false;
        this.cookiesToDB = false;
    }

    setTitle(text: string) {
        this.title = text;
    }

    setContext(text: string) {
        this.context = text;
    }
    
    setDetails(text: string) {
        this.details = text;
    }

    setLogout(value: boolean) {
        this.logout = value;
    }

    setDeleteImage(value: boolean) {
        this.deleteImage = value;
    }

    setCookies(value: boolean) {
        this.cookies = value;
    }

    setCookiesToDB(value: boolean) {
        this.cookiesToDB = value;
    }

    getTitle() { return this.title }
    getContext() { return this.context }
    getDetails() { return this.details }
    isLogout() { return this.logout }
    isDeleteImage() { return this.deleteImage }
    isCookies() { return this.cookies }
    isCookiesToDB() { return this.cookiesToDB }
    isActivated() { return this.active }
}