export class WindowPopService {
    private active: Boolean = false;
    private title: string;
    private context: string;
    private details: string;
    private logout: Boolean = false;
    private deleteImage: Boolean = false;
    private cookies: Boolean = false;
    private cookiesToDB: Boolean = false;
    private deleteProject: Boolean = false;
    private deleteProduct: Boolean = false;

    activate() {
        this.active = true;
    }

    deactivate() {
        this.active = false;
        this.logout = false;
        this.deleteImage = false;
        this.cookies = false;
        this.cookiesToDB = false;
        this.deleteProject = false;
        this.deleteProduct = false;
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

    setDeleteProduct(value: boolean) {
        this.deleteProduct = value;
    }

    setDeleteProject(value: boolean) {
        this.deleteProject = value;
    }

    setCookies(value: boolean) {
        this.cookies = value;
    }

    setCookiesToDB(value: boolean) {
        this.cookiesToDB = value;
    }

    getTitle() { return this.title; }
    getContext() { return this.context; }
    getDetails() { return this.details; }
    isLogout() { return this.logout; }
    isDeleteImage() { return this.deleteImage; }
    isDeleteProject() { return this.deleteProject; }
    isDeleteProduct() { return this.deleteProduct; }
    isCookies() { return this.cookies; }
    isCookiesToDB() { return this.cookiesToDB; }
    isActivated() { return this.active; }
}
