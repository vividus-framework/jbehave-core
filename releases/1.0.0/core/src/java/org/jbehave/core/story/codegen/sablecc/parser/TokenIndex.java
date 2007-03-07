/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jbehave.core.story.codegen.sablecc.parser;

import org.jbehave.core.story.codegen.sablecc.analysis.AnalysisAdapter;
import org.jbehave.core.story.codegen.sablecc.node.EOF;
import org.jbehave.core.story.codegen.sablecc.node.TAsA;
import org.jbehave.core.story.codegen.sablecc.node.TEndl;
import org.jbehave.core.story.codegen.sablecc.node.TGiven;
import org.jbehave.core.story.codegen.sablecc.node.TIWant;
import org.jbehave.core.story.codegen.sablecc.node.TScenarioKeyword;
import org.jbehave.core.story.codegen.sablecc.node.TSoThat;
import org.jbehave.core.story.codegen.sablecc.node.TSpace;
import org.jbehave.core.story.codegen.sablecc.node.TThen;
import org.jbehave.core.story.codegen.sablecc.node.TTitleKeyword;
import org.jbehave.core.story.codegen.sablecc.node.TWhen;
import org.jbehave.core.story.codegen.sablecc.node.TWord;

class TokenIndex extends AnalysisAdapter
{
    int index;

    public void caseTTitleKeyword(TTitleKeyword node)
    {
        index = 0;
    }

    public void caseTScenarioKeyword(TScenarioKeyword node)
    {
        index = 1;
    }

    public void caseTAsA(TAsA node)
    {
        index = 2;
    }

    public void caseTIWant(TIWant node)
    {
        index = 3;
    }

    public void caseTSoThat(TSoThat node)
    {
        index = 4;
    }

    public void caseTGiven(TGiven node)
    {
        index = 5;
    }

    public void caseTWhen(TWhen node)
    {
        index = 6;
    }

    public void caseTThen(TThen node)
    {
        index = 7;
    }

    public void caseTSpace(TSpace node)
    {
        index = 8;
    }

    public void caseTWord(TWord node)
    {
        index = 9;
    }

    public void caseTEndl(TEndl node)
    {
        index = 10;
    }

    public void caseEOF(EOF node)
    {
        index = 11;
    }
}
