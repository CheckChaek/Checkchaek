import { Book, HistoriesResponse } from '../../../interface/api';

class Histories {
  code: string;

  message: string;

  data: Map<string, Book[]>;

  constructor(response: HistoriesResponse) {
    this.code = response.code;
    this.message = response.message;
    this.data = response.data;
  }

  public get Code(): string {
    return this.code;
  }

  public get Message(): string {
    return this.message;
  }

  public get Data(): Map<string, Book[]> {
    return this.data;
  }
}

export default Histories;
