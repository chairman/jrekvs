/**
 * hash op
 * @author Tommy.z
 *
 */
package io.jrekvs.hash;

import io.jrekvs.enums.hash.Hash_func_type;

public interface Hash_init extends Hash {
    int hash_init(Hash_func_type type);
}
