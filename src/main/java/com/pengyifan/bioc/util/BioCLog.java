package com.pengyifan.bioc.util;

import com.google.common.base.Joiner;
import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCLocation;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCObject;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;
import java.util.StringJoiner;

/**
 * TODO: move log into structure
 */
public class BioCLog {

  private static final Joiner joiner = Joiner.on('/');

  public static String log(BioCObject obj) {
    if (obj instanceof BioCCollection) {
      return String.format("collection[src=%s]", ((BioCCollection) obj).getSource());
    } else if (obj instanceof BioCDocument) {
      return String.format("document[id=%s]", ((BioCDocument) obj).getID());
    } else if (obj instanceof BioCPassage) {
      return String.format("passage[offset=%d]", ((BioCPassage) obj).getOffset());
    } else if (obj instanceof BioCSentence) {
      return String.format("sentence[offset=%d]", ((BioCSentence) obj).getOffset());
    } else if (obj instanceof BioCAnnotation) {
      return String.format("annotation[id=%s]", ((BioCAnnotation) obj).getID());
    } else if (obj instanceof BioCRelation) {
      return String.format("relation[id=%s]", ((BioCRelation) obj).getID());
    } else if (obj instanceof BioCNode) {
      return String.format("node[id=%s]", ((BioCNode) obj).getRefid());
    } else if (obj instanceof BioCLocation) {
      return obj.toString();
    } else {
      return null;
    }
  }

  public static String log(BioCObject first, BioCObject ... rest) {
    StringJoiner sj = new StringJoiner("/");
    sj.add(log(first));
    for (BioCObject o: rest) {
      sj.add(log(o));
    }
    return sj.toString();
  }

  public static String log(BioCObject[] objects) {
    StringJoiner sj = new StringJoiner("/");
    for (BioCObject o: objects) {
      sj.add(log(o));
    }
    return sj.toString();
  }

  public static String logInfon(String key, Object value) {
    return String.format("infon[@key='%s']: %s", key, value.toString());
  }

  public static String logInfon(String key, Object value, BioCObject first, BioCObject ... rest) {
    return joiner.join(log(first, rest), logInfon(key, value));
  }

  public static String logText(String text) {
    return "text:" + text;
  }

  public static String logText(String text, BioCObject first, BioCObject ... rest) {
    return joiner.join(log(first, rest), logText(text));
  }
}
