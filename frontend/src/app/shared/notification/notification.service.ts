import { timeout } from 'q';

export class NotificationService {
    private activate: Boolean = false;
    private timeout;

    showNotification() {
        this.activate = true;
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
            this.activate = false;
        }, 4000);
    }

    hideNotification() {
        this.activate = false;
        clearTimeout(this.timeout);
    }

    isActivated() {
        return this.activate;
    }

}