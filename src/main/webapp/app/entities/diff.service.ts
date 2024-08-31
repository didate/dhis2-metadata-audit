import { Injectable } from '@angular/core';
import { diff_match_patch, Diff } from 'diff-match-patch';

@Injectable({
  providedIn: 'root',
})
export class DiffService {
  //private dmp: diff_match_patch;
  private dmp: InstanceType<typeof diff_match_patch>;
  constructor() {
    this.dmp = new diff_match_patch();
  }

  generateDiff(text1: string, text2: string): string {
    const diffs: Diff[] = this.dmp.diff_main(`${text1}`, `${text2}`);
    this.dmp.diff_cleanupSemantic(diffs);
    return this.dmp.diff_prettyHtml(diffs);
  }
}
